package com.example.demo.e2e;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.function.Function;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("default")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminVetTreatmentE2ETest {

    private final String BASE_URL = "http://localhost:4200";
    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;

    @BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");

        // chromeOptions.addArguments("--headless");
        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        driver.manage().window().maximize();
    }

    @Test
    public void adminVetTreatmentWorkflowTest() {
        // 1. Login admin y guardar valores previos
        DashboardMetrics beforeMetrics = getDashboardMetricsAsAdmin();

        // 2. Login como veterinario y dar tratamiento a Firulais con AIVLOSIN
        giveTreatmentToFirulaisAsVet();

        // 3. Verificar en historial de Firulais que el tratamiento fue registrado
        verifyTreatmentInPetHistory();

        // 4. Login admin y comparar métricas
        DashboardMetrics afterMetrics = getDashboardMetricsAsAdmin();

        // 5. Verificaciones finales
        if (beforeMetrics.aivlosinCount == -1) {
            Assertions.assertThat(afterMetrics.aivlosinCount)
                    .isEqualTo(1);
        } else {
            Assertions.assertThat(afterMetrics.aivlosinCount)
                    .isEqualTo(beforeMetrics.aivlosinCount + 1);
        }
        float expectedVentas = beforeMetrics.ventasTotales + 164900f;
        Assertions.assertThat(afterMetrics.ventasTotales)
                .as("Las ventas después del tratamiento deben ser exactamente las ventas anteriores + $115430")
                .isCloseTo(expectedVentas, Assertions.within(0.01f));
    }

    // --- Utilidades y helpers ---

    private DashboardMetrics getDashboardMetricsAsAdmin() {
        driver.get(BASE_URL + "/login/admin");
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cedula")));
        sendKeysWithRetry(By.id("cedula"), "ADMIN001");
        sendKeysWithRetry(By.id("password"), "password");
        simulateCaptchaAndWaitForSubmitEnabled();
        clickWithRetry(By.cssSelector("button[type='submit']"));

        // --- NUEVO: aceptar cualquier alerta modal (incluyendo la de Chrome) ---
        acceptAnyAlertIfPresent();

        wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dashboard-content")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[contains(@class,'detail-table')]")));
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@class,'detail-table')][1]//tbody/tr"));
        int aivlosinCount = -1;
        for (WebElement row : rows) {
            List<WebElement> tds = row.findElements(By.tagName("td"));
            if (tds.size() >= 2 && tds.get(0).getText().trim().equalsIgnoreCase("AIVLOSIN")) {
                aivlosinCount = Integer.parseInt(tds.get(1).getText().trim());
                break;
            }
        }
        // Si no se encontró, imprime los medicamentos para depuración
        if (aivlosinCount == -1) {
            System.out.println("Medicamentos encontrados en tabla:");
            for (WebElement row : rows) {
                List<WebElement> tds = row.findElements(By.tagName("td"));
                if (tds.size() >= 2) {
                    System.out.println(" - " + tds.get(0).getText());
                }
            }
        }
        // No hacer aserción aquí, se maneja en el test principal

        // Buscar la tarjeta de ganancias de forma robusta
        List<WebElement> metricCards = driver.findElements(By.cssSelector(".metric-card"));
        String ventasText = null;
        for (WebElement card : metricCards) {
            List<WebElement> headers = card.findElements(By.tagName("h3"));
            if (!headers.isEmpty() && headers.get(0).getText().trim().equalsIgnoreCase("Ventas Totales")) {
                WebElement valueDiv = card.findElement(By.cssSelector(".metric-value"));
                ventasText = valueDiv.getText();
                break;
            }
        }
        if (ventasText == null) {
            throw new NoSuchElementException("No se encontró la tarjeta de 'Ventas Totales' en el dashboard.");
        }
        // Limpiar el texto de ventas para obtener solo el número
        ventasText = ventasText.replaceAll("[^\\d.,]", "").trim();

        // Si el formato es "840,870.00" (coma miles, punto decimal), elimina todas las comas
        if (ventasText.matches(".*\\d,\\d{3}\\.\\d{2}$")) {
            ventasText = ventasText.replace(",", "");
        }
        // Si el formato es "840.870,00" (punto miles, coma decimal), elimina puntos y cambia coma por punto
        else if (ventasText.matches(".*\\d\\.\\d{3},\\d{2}$")) {
            ventasText = ventasText.replace(".", "").replace(",", ".");
        }
        // Si solo hay coma decimal, reemplaza por punto
        else if (ventasText.contains(",") && !ventasText.contains(".")) {
            ventasText = ventasText.replace(",", ".");
        }

        float ventasTotales = Float.parseFloat(ventasText);

        return new DashboardMetrics(aivlosinCount, ventasTotales);
    }

    // --- NUEVO: método para aceptar cualquier alerta modal ---
    private void acceptAnyAlertIfPresent() {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            alertWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (TimeoutException | NoAlertPresentException ignored) {
            // No hay alerta, continuar
        }
    }

    private void giveTreatmentToFirulaisAsVet() {
        // Login vet
        driver.get(BASE_URL + "/login/vet");
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cedula")));
        sendKeysWithRetry(By.id("cedula"), "VET12345");
        sendKeysWithRetry(By.id("password"), "password");
        simulateCaptchaAndWaitForSubmitEnabled();
        clickWithRetry(By.cssSelector("button[type='submit']"));
        wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dashboard-content")));

        // Buscar "Firulais"
        WebElement searchInput = findElement(By.cssSelector("input[type='search'], input[placeholder*='Buscar']"));
        searchInput.clear();
        searchInput.sendKeys("Firulais");
        searchInput.sendKeys(Keys.ENTER); // Asegura que se dispare la búsqueda
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody/tr")));

        // Buscar la fila que contiene a Firulais
        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody/tr"));
        WebElement firulaisRow = null;
        for (WebElement row : rows) {
            WebElement nameCell = row.findElement(By.cssSelector("a.pet-link"));
            if (nameCell.getText().trim().equalsIgnoreCase("Firulais")) {
                firulaisRow = row;
                break;
            }
        }
        Assertions.assertThat(firulaisRow)
                .as("No se encontró la fila de Firulais en la tabla de mascotas.")
                .isNotNull();

        WebElement actionsCell = firulaisRow.findElement(By.cssSelector("td.actions-cell"));
        System.out.println("HTML de la celda de acciones de Firulais:");
        System.out.println(actionsCell.getAttribute("innerHTML"));

        // Esperar a que el botón esté visible y habilitado
        WebElement giveTreatmentBtn = fluentWait.withTimeout(Duration.ofSeconds(30)).until(driver -> {
            List<WebElement> buttons = actionsCell.findElements(By.cssSelector("button.treatment-button"));
            for (WebElement btn : buttons) {
                boolean displayed = btn.isDisplayed();
                boolean enabled = btn.isEnabled();
                String text = btn.getText().toLowerCase();
                System.out.printf("Botón encontrado: displayed=%s, enabled=%s, text=%s%n", displayed, enabled, text);
                if (displayed && enabled && text.contains("dar tratamiento")) {
                    return btn;
                }
            }
            return null;
        });

        Assertions.assertThat(giveTreatmentBtn)
                .as("No se encontró el botón 'Dar Tratamiento' habilitado y visible en la fila de Firulais. HTML de la celda: "
                        + actionsCell.getAttribute("innerHTML"))
                .isNotNull();

        // Forzar el click con JS por robustez
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", giveTreatmentBtn);

        // Esperar carga de formulario
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("droga")));
        WebElement select = driver.findElement(By.id("droga"));
        List<WebElement> options = select.findElements(By.tagName("option"));
        WebElement aivlosinOption = null;
        for (WebElement option : options) {
            if (option.getText().toUpperCase(Locale.ROOT).contains("AIVLOSIN")) {
                aivlosinOption = option;
                break;
            }
        }
        Assertions.assertThat(aivlosinOption)
                .as("No se encontró la opción 'AIVLOSIN' en el select de medicamentos. Opciones: " +
                        options.stream().map(WebElement::getText).toList())
                .isNotNull();
        aivlosinOption.click();

        // Espera a que el botón submit esté habilitado
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit'],button.submit-button"));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        submitBtn.click();

        // Esperar alerta de éxito y redirección
        wait.until(ExpectedConditions.or(
                ExpectedConditions.alertIsPresent(),
                ExpectedConditions.urlContains("/vets/dashboard")));
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
        }
        wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
        waitForPageLoad();
    }

    private void verifyTreatmentInPetHistory() {
        // Buscar "Firulais" de nuevo
        WebElement searchInput = findElement(By.cssSelector("input[type='search'], input[placeholder*='Buscar']"));
        searchInput.clear();
        searchInput.sendKeys("Firulais");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody/tr")));
        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody/tr"));
        WebElement firulaisRow = null;
        for (WebElement row : rows) {
            WebElement nameCell = row.findElement(By.cssSelector("a.pet-link"));
            if (nameCell.getText().trim().equalsIgnoreCase("Firulais")) {
                firulaisRow = row;
                break;
            }
        }
        Assertions.assertThat(firulaisRow)
                .as("No se encontró la fila de Firulais en la tabla de mascotas.")
                .isNotNull();

        // Hacer click en el enlace de detalle de Firulais
        WebElement petLink = firulaisRow.findElement(By.cssSelector("a.pet-link"));
        petLink.click();

        // Esperar carga de historial médico (ajusta el selector según tu HTML real)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(@class,'historial-section')]//table")));

        // Verificar que el último registro tiene "AIVLOSIN"
        List<WebElement> historyRows = driver.findElements(By.xpath("//article[contains(@class,'historial-section')]//table//tbody/tr"));
        Assertions.assertThat(historyRows.size()).isGreaterThan(0);
        WebElement lastRow = historyRows.get(0); // Asumimos orden descendente por fecha
        List<WebElement> tds = lastRow.findElements(By.tagName("td"));
        boolean found = false;
        for (WebElement td : tds) {
            if (td.getText().toUpperCase(Locale.ROOT).contains("AIVLOSIN")) {
                found = true;
                break;
            }
        }
        Assertions.assertThat(found)
                .as("No se encontró el tratamiento 'AIVLOSIN' en el historial de Firulais.")
                .isTrue();
    }

    // --- Utilidades Selenium ---

    private void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete");
        wait.until(pageLoadCondition);
    }

    private WebElement findElement(By locator) {
        return fluentWait.until(driver -> driver.findElement(locator));
    }

    private void clickWithRetry(By locator) {
        fluentWait.until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    private void sendKeysWithRetry(By locator, String text) {
        fluentWait.until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                if (!element.isDisplayed() || !element.isEnabled())
                    return false;
                element.click(); // Asegura el focus y dispara binding Angular
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
                element.clear();
                element.sendKeys(text);
                return element.getAttribute("value").equals(text) || element.getAttribute("value").endsWith(text);
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    private void simulateCaptchaAndWaitForSubmitEnabled() {
        try {
            WebElement captcha = findElement(By.tagName("app-captcha"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new CustomEvent('captchaResolved', { detail: 'token123' }));",
                    captcha);
        } catch (Exception ignored) {
        }
        // Espera a que el botón de submit esté habilitado
        wait.until(driver -> {
            WebElement btn = driver.findElement(By.cssSelector("button[type='submit']"));
            return btn.isEnabled();
        });
    }

    @AfterEach
    public void tearDown() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        if (driver != null)
            driver.quit();
    }

    // --- DTO para métricas del dashboard ---
    private static class DashboardMetrics {
        int aivlosinCount;
        float ventasTotales;

        DashboardMetrics(int aivlosinCount, float ventasTotales) {
            this.aivlosinCount = aivlosinCount;
            this.ventasTotales = ventasTotales;
        }
    }
}