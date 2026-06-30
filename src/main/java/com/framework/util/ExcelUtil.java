package com.framework.util;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
    private final String filePath;
    private final DataFormatter formatter = new DataFormatter();

    public ExcelUtil(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getRowData(String sheetName, String testCaseId) {
        try (FileInputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row header = sheet.getRow(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String currentTc = formatter.formatCellValue(row.getCell(0)).trim();
                if (testCaseId.equalsIgnoreCase(currentTc)) {
                    Map<String, String> data = new HashMap<>();
                    for (int c = 0; c < header.getLastCellNum(); c++) {
                        String key = formatter.formatCellValue(header.getCell(c)).trim();
                        String value = formatter.formatCellValue(row.getCell(c)).trim();
                        data.put(key, value);
                    }
                    return data;
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to read test data file: " + filePath, exception);
        }

        throw new IllegalArgumentException("Test data not found for " + testCaseId + " in " + sheetName);
    }
}
