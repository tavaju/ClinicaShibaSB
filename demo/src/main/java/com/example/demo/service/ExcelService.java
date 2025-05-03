package com.example.demo.service;

import com.example.demo.model.Droga;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public List<Droga> readDrogasFromExcel(InputStream inputStream) throws Exception {
        List<Droga> drogas = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowCount; i++) { // Skip header row
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                Droga droga = new Droga();
                droga.setNombre(getCellValueAsString(row.getCell(0)));
                droga.setPrecioVenta(parseFloat(getCellValueAsString(row.getCell(1))));
                droga.setPrecioCompra(parseFloat(getCellValueAsString(row.getCell(2))));
                droga.setUnidadesDisponibles(parseInt(getCellValueAsString(row.getCell(3))));
                droga.setUnidadesVendidas(parseInt(getCellValueAsString(row.getCell(4))));

                drogas.add(droga);
            }
        }

        return drogas;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null)
            return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? cell.getDateCellValue().toString()
                    : String.valueOf((cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue()))
                            ? (long) cell.getNumericCellValue()
                            : cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private float parseFloat(String value) {
        try {
            return Float.parseFloat(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid float value: " + value);
        }
    }

    private int parseInt(String value) {
        try {
            // Primero intenta como entero directo
            return Integer.parseInt(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            // Si falla, intenta como float y conviértelo a entero si termina en ".0"
            try {
                float f = Float.parseFloat(value.replace(",", "").trim());
                if (f == Math.floor(f)) {
                    return (int) f;
                } else {
                    throw new IllegalArgumentException("Expected integer but found decimal: " + value);
                }
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid integer value: " + value);
            }
        }
    }

}
