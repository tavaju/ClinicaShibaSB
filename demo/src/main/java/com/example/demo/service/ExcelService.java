package com.example.demo.service;

import com.example.demo.model.Droga;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public List<Droga> readDrogasFromExcel(String filePath) throws Exception {
        List<Droga> drogas = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook = new XSSFWorkbook(fis)) {

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
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Remove ".0" if the number is an integer
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue); // Convert to long if it's an integer
                    } else {
                        return String.valueOf(numericValue); // Keep as double if it's a decimal
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
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
            return Integer.parseInt(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer value: " + value);
        }
    }
}