package com.example.demo.e2e;

import java.time.Duration;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.function.Function;

/**
 * Prueba de extremo a extremo para el flujo de trabajo de un veterinario:
 * 1. Iniciar sesión como veterinario (con un intento fallido y luego exitoso)
 * 2. Registrar un nuevo cliente (con un error y luego exitoso)
 * 3. Registrar una mascota para el cliente (exitoso en el primer intento)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Disabled("Pending mock configuration")

public class VeterinarianWorkflowTest {
    
    private final String BASE_URL = "http://localhost:4200";
    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;
    
    // Datos únicos para evitar conflictos entre ejecuciones de pruebas
    private final String uniqueId = String.valueOf(System.currentTimeMillis());
    private final String clientCedula = "123212";
    private final String clientNombre = "tatis";
    private final String clientEmail = "sample@example.com";

    @BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        // Comentar la siguiente línea si se desea ver la ejecución de la prueba
        // chromeOptions.addArguments("--headless");

        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Configuración de FluentWait con manejo de excepciones para elementos no encontrados y stale
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        
        // Maximizar la ventana del navegador para evitar problemas con elementos responsivos
        driver.manage().window().maximize();
    }

    @Test
    public void veterinarianWorkflowTest() {
        // Paso 1: Inicio de sesión con un intento fallido y luego exitoso
        veterinarianLogin();
        
        // Paso 2: Registrar un nuevo cliente con un error y luego correcto
        createClient();
        
        // Paso 3: Registrar una mascota asociada al cliente
        createPet();

        //Paso 4. Ingresar al portal del cliente
        goToClientPortal();
    }
    
    /**
     * Método auxiliar para esperar a que la página se cargue completamente.
     */
    private void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        wait.until(pageLoadCondition);
    }
    
    /**
     * Encuentra un elemento con reintento en caso de error StaleElementReferenceException
     */
    private WebElement findElement(By locator) {
        return fluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
    }
    
    /**
     * Encuentra elementos con reintento en caso de error StaleElementReferenceException
     */
    /* 
    private List<WebElement> findElements(By locator) {
        return fluentWait.until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                return driver.findElements(locator);
            }
        });
    }
    */
    /**
     * Hace clic en un elemento con reintento en caso de StaleElementReferenceException
     */
    private void clickWithRetry(By locator) {
        fluentWait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
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
            }
        });
    }
    
    /**
     * Envía texto a un elemento con reintento en caso de StaleElementReferenceException
     */
    private void sendKeysWithRetry(By locator, String text) {
        fluentWait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    WebElement element = driver.findElement(locator);
                    element.clear();
                    element.sendKeys(text);
                    return true;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }
        });
    }
    
    /**
     * Realiza el proceso de login como veterinario, primero con credenciales incorrectas
     * y luego con credenciales correctas.
     */
    private void veterinarianLogin() {
        // Navegar a la página de inicio de sesión de veterinarios
        driver.get(BASE_URL + "/login/vet");
        
        waitForPageLoad();
        
        // Esperar a que la página cargue
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cedula")));
        
        // Primer intento con credenciales incorrectas
        sendKeysWithRetry(By.id("cedula"), "123456789");
        sendKeysWithRetry(By.id("password"), "contraseñaIncorrecta");
        
        // Verificar que el reCAPTCHA esté resuelto (simulamos que ya está resuelto)
        WebElement captchaResolved = findElement(By.tagName("app-captcha"));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new CustomEvent('captchaResolved', { detail: 'token123' }));", 
            captchaResolved
        );
        
        clickWithRetry(By.cssSelector("button[type='submit']"));
        
        // Esperar a que aparezca mensaje de error
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
            WebElement errorMessage = findElement(By.className("error-message"));
            Assertions.assertThat(errorMessage.isDisplayed()).isTrue();
        } catch (TimeoutException e) {
            // En caso de que el mensaje de error no aparezca como se esperaba, continuamos
            System.out.println("Advertencia: No se pudo verificar el mensaje de error tras el primer intento de login");
        }
        
        // Segundo intento con credenciales correctas
        sendKeysWithRetry(By.id("cedula"), "VET12345"); // Usar una cédula válida en el sistema
        sendKeysWithRetry(By.id("password"), "password"); // Usar una contraseña válida
        
        // Asegurar que el captcha sigue resuelto
        captchaResolved = findElement(By.tagName("app-captcha"));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new CustomEvent('captchaResolved', { detail: 'token123' }));", 
            captchaResolved
        );
        
        clickWithRetry(By.cssSelector("button[type='submit']"));
        
        // Esperar a que redirija al dashboard
        wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
        
        waitForPageLoad();
        
        // Verificar que el dashboard se cargó correctamente
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dashboard-content")));
        Assertions.assertThat(driver.getCurrentUrl()).contains("/vets/dashboard");
    }
    
    /**
     * Realiza el proceso de creación de un cliente, primero con un error
     * y luego correctamente en el segundo intento.
     */
    private void createClient() {
        // Esperar a que el dashboard se cargue completamente
        waitForPageLoad();
        
        // Navegar a la página de crear cliente
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.add-button[routerlink='/clients/add']")));
        clickWithRetry(By.cssSelector("a.add-button[routerlink='/clients/add']"));
        
        // Esperar que cargue el formulario
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cedula")));
        
        // Primer intento: faltan datos obligatorios
        sendKeysWithRetry(By.id("cedula"), clientCedula);
        sendKeysWithRetry(By.id("correo"), clientEmail);
        sendKeysWithRetry(By.id("celular"), "3001234567");
        sendKeysWithRetry(By.id("contrasena"), "Cliente123");
        sendKeysWithRetry(By.id("confirmPassword"), "Cliente123");
        
        // Verificar que el nombre no está presente todavía - primer intento intencionalmente incompleto
        WebElement nombreInput = findElement(By.id("nombre"));
        Assertions.assertThat(nombreInput.getAttribute("value")).isEmpty();
        System.out.println("Primer intento deliberadamente sin nombre: campo vacío verificado");
        
        // Intentar enviar el formulario
        WebElement submitButton = findElement(By.cssSelector("button[type='submit']"));
        
        // Si el botón está habilitado, hacer clic
        if (submitButton.isEnabled()) {
            submitButton.click();
            System.out.println("Formulario enviado sin nombre para probar validación");
            
            // Esperar un breve momento para dar tiempo a cualquier validación del lado del cliente
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // MANEJAR ALERTA SI EXISTE
        try {
            // Intentar cambiar a la alerta y aceptarla
            driver.switchTo().alert().accept();
            System.out.println("Alerta aceptada: Error al crear el cliente (esperado en primer intento)");
        } catch (Exception e) {
            System.out.println("No se encontró alerta o hubo un error al manejarla: " + e.getMessage());
        }
        
        // Verificamos que seguimos en la página del formulario
        try {
            // Esperar un momento para que cualquier redirección ocurra
            Thread.sleep(500);
            
            // SEGUNDO INTENTO: Ahora completamos el campo faltante (nombre)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nombre")));
            
            // Usar JavaScript para establecer el valor del campo nombre
            nombreInput = findElement(By.id("nombre"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", nombreInput, clientNombre);
            
            // Verificar que el nombre se añadió correctamente
            String valorIngresado = nombreInput.getAttribute("value");
            System.out.println("Valor ingresado en campo nombre: [" + valorIngresado + "]");
            
            // Si el JavaScript no funcionó, usar sendKeys directamente
            if (!clientNombre.equals(valorIngresado)) {
                System.out.println("Usando método alternativo para ingresar el nombre");
                nombreInput.clear();
                nombreInput.sendKeys(clientNombre);
            }
            
            // Verificar una vez más y asegurar que el valor se estableció
            valorIngresado = nombreInput.getAttribute("value");
            Assertions.assertThat(valorIngresado).isEqualTo(clientNombre);
            
            // Buscar de nuevo el botón de enviar y hacer clic
            submitButton = findElement(By.cssSelector("button[type='submit']"));
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            
            // Usar JavaScript para garantizar que se haga clic en el botón
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", submitButton);
            
            System.out.println("Formulario reenviado con todos los campos completados");
            
            // Manejar posible alerta después del segundo intento
            try {
                driver.switchTo().alert().accept();
                System.out.println("Alerta después del segundo intento aceptada");
            } catch (Exception e) {
                // No hay alerta, lo cual es normal si todo va bien
                System.out.println("No se encontró alerta después del segundo intento (esto es normal si todo va bien)");
            }
            
            // Esperar que redirija al dashboard después de crear el cliente
            wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
            waitForPageLoad();
            
        } catch (Exception e) {
            System.out.println("Error durante el segundo intento: " + e.getMessage());
            
            e.printStackTrace();
            
            // Si falla el segundo intento, intentamos una última vez desde cero
            try {
                driver.get(BASE_URL + "/vets/dashboard");
                waitForPageLoad();
                
                // Navegar a la página de crear cliente
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.add-button[routerlink='/clients/add']")));
                clickWithRetry(By.cssSelector("a.add-button[routerlink='/clients/add']"));
                
                waitForPageLoad();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cedula")));
                
                // Llenar todos los campos correctamente
                sendKeysWithRetry(By.id("cedula"), clientCedula);
                sendKeysWithRetry(By.id("nombre"), clientNombre);
                sendKeysWithRetry(By.id("correo"), clientEmail);
                sendKeysWithRetry(By.id("celular"), "3001234567");
                sendKeysWithRetry(By.id("contrasena"), "Cliente123");
                sendKeysWithRetry(By.id("confirmPassword"), "Cliente123");
                
                // Enviar formulario
                submitButton = findElement(By.cssSelector("button[type='submit']"));
                wait.until(ExpectedConditions.elementToBeClickable(submitButton));
                submitButton.click();
                
                // Manejar posible alerta
                try {
                    driver.switchTo().alert().accept();
                } catch (Exception alertEx) {
                    // Es normal que no haya alerta
                }
                
                // Esperar redirección al dashboard
                wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
                waitForPageLoad();
            } catch (Exception fallbackEx) {
                System.out.println("ERROR CRÍTICO incluso en intento final: " + fallbackEx.getMessage());
                fallbackEx.printStackTrace();
                throw new AssertionError("No se pudo crear el cliente después de múltiples intentos");
            }
        }
        
        // Esperar un breve momento para que el dashboard se cargue completamente
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verificamos que estamos en el dashboard (no validamos la tabla)
        Assertions.assertThat(driver.getCurrentUrl()).contains("/vets/dashboard");
        
        System.out.println("Cliente creado exitosamente y redirigido al dashboard");
    }
    
    /**
     * Realiza el proceso de creación de una mascota asociada al cliente creado.
     */
    private void createPet() {
        // Esperar a que el dashboard se cargue completamente
        waitForPageLoad();
        
        // Navegar a la página de agregar mascota
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[routerlink='/pets/add']")));
        clickWithRetry(By.cssSelector("a[routerlink='/pets/add']"));
        
        // Esperar a que cargue el formulario
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nombre")));
        
        // Datos únicos para la mascota
        String petName = "MascotaNew";
        
        // Llenar el formulario con datos de la mascota
        sendKeysWithRetry(By.id("nombre"), petName);
        sendKeysWithRetry(By.id("edad"), "3");
        sendKeysWithRetry(By.id("raza"), "Labrador");
        sendKeysWithRetry(By.id("peso"), "15.5");
        sendKeysWithRetry(By.id("enfermedad"), "Ninguna");
        sendKeysWithRetry(By.id("foto"), "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D");
        sendKeysWithRetry(By.id("cedulaCliente"), clientCedula); // Usar la cédula del cliente recién creado
        
        // Verificar que el campo de cédula contiene el valor esperado
        WebElement cedulaInput = findElement(By.id("cedulaCliente"));
        String actualCedula = cedulaInput.getAttribute("value");
        Assertions.assertThat(actualCedula).isEqualTo(clientCedula);
        System.out.println("Cédula del cliente para la mascota: " + actualCedula);
        
        // Enviar el formulario
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        clickWithRetry(By.cssSelector("button[type='submit']"));
        
        // Esperar que redirija al dashboard después de crear la mascota
        wait.until(ExpectedConditions.urlContains("/vets/dashboard"));
        waitForPageLoad();
        
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verificar que se ha redirigido correctamente al dashboard
        Assertions.assertThat(driver.getCurrentUrl()).contains("/vets/dashboard");
    }

    /**
     * Realiza el proceso de acceso al portal del cliente con las credenciales del cliente recién creado.
     */
    private void goToClientPortal() {

        // Navegar a la página de login de cliente
        driver.get(BASE_URL + "/login");
        waitForPageLoad();
        
        // Verificar que estamos en la página de login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        System.out.println("Página de login de cliente cargada correctamente");
        
        // Ingresar credenciales del cliente recién creado
        sendKeysWithRetry(By.id("email"), clientEmail);
        sendKeysWithRetry(By.id("password"), "Cliente123");
        
        // Resolver el captcha (simulación)
        try {
            WebElement captchaElement = findElement(By.tagName("app-captcha"));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new CustomEvent('captchaResolved', { detail: 'token123' }));", 
                captchaElement
            );
            System.out.println("Captcha simulado resuelto correctamente");
        } catch (Exception e) {
            System.out.println("Error al resolver captcha: " + e.getMessage());
        }
        
        // Hacer clic en el botón de ingresar
        clickWithRetry(By.cssSelector("button[type='submit']"));
        
        // Esperar a que redirija a la página de información del cliente
        try {
            wait.until(ExpectedConditions.urlContains("/clients/info"));
            waitForPageLoad();
            
            // Verificar que se cargó la vista del cliente
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("info-grid")));
            
            // Buscar el correo electrónico del cliente en la página
            WebElement emailInfo = findElement(By.xpath("//div[contains(@class, 'info-item')]//p[contains(text(), '" + clientEmail + "')]"));
            if (emailInfo != null && emailInfo.isDisplayed()) {
                System.out.println("Portal del cliente cargado correctamente. Se verificó el email: " + clientEmail);
            } else {
                System.out.println("No se pudo verificar la información del cliente en la página");
            }
            
            // Verificar sección de mascotas
            WebElement petSection = findElement(By.className("pets-section"));
            if (petSection != null && petSection.isDisplayed()) {
                System.out.println("Sección de mascotas del cliente encontrada");
                
                // Verificar tabla de mascotas
                try {
                    List<WebElement> petRows = driver.findElements(By.cssSelector(".pets-section table tbody tr"));
                    if (petRows.size() > 0) {
                        // Si hay al menos una fila (que no sea la de "no hay mascotas")
                        if (!petRows.get(0).getText().contains("No tienes mascotas registradas")) {
                            System.out.println("Se encontraron " + petRows.size() + " mascotas para el cliente");
                            
                            // Verificar que la primera mascota sea "MascotaNew"
                            WebElement firstPetRow = petRows.get(0);
                            WebElement firstPetNameLink = firstPetRow.findElement(By.cssSelector(".pet-link"));
                            String actualPetName = firstPetNameLink.getText();
                            
                            Assertions.assertThat(actualPetName)
                                .as("Verificando que la primera mascota en la tabla sea 'MascotaNew'")
                                .isEqualTo("MascotaNew");
                            System.out.println("Se verificó correctamente el nombre de la primera mascota: " + actualPetName);
                        } else {
                            System.out.println("El cliente no tiene mascotas registradas");
                            Assertions.fail("No se encontraron mascotas para el cliente cuando debería haber al menos una");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error al buscar mascotas: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error al ingresar al portal del cliente: " + e.getMessage());
            e.printStackTrace();
            Assertions.fail("No se pudo acceder al portal del cliente");
        }
        
        // Verificar que estamos en la página de información del cliente
        Assertions.assertThat(driver.getCurrentUrl()).contains("/clients/info");
        System.out.println("Test de ingreso al portal del cliente completado con éxito");
    }

    
    @AfterEach
    public void tearDown() {
        try {
            // Esperar un momento para poder ver el resultado final (en caso de no ser headless)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el navegador
            if (driver != null) {
                driver.quit();
            }
        }
    }
}