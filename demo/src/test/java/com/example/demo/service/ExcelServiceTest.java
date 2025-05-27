package com.example.demo.service;

import com.example.demo.model.Droga;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExcelServiceTest {

    @Autowired
    private ExcelService excelService;

    @Test
    public void testReadDrogasFromExcelInputStream() throws Exception {
        // Load the Excel file from classpath as InputStream
        String excelFilePath = "excel/MEDICAMENTOS_VETERINARIA.xlsx";
        ClassLoader classLoader = getClass().getClassLoader();
        
        try (InputStream inputStream = classLoader.getResourceAsStream(excelFilePath)) {
            assertNotNull(inputStream, "Excel file should be found in classpath");
            
            List<Droga> drogas = excelService.readDrogasFromExcel(inputStream);
            
            assertNotNull(drogas, "Drogas list should not be null");
            assertFalse(drogas.isEmpty(), "Drogas list should not be empty");
            
            // Verify that at least one droga has valid data
            Droga firstDroga = drogas.get(0);
            assertNotNull(firstDroga.getNombre(), "Droga name should not be null");
            assertTrue(firstDroga.getPrecioVenta() > 0, "Precio venta should be positive");
            assertTrue(firstDroga.getPrecioCompra() > 0, "Precio compra should be positive");
            
            System.out.println("Successfully loaded " + drogas.size() + " drogas from Excel via InputStream");
        }
    }
} 