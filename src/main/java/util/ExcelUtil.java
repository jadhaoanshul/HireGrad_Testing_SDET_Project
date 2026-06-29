package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil implements FrameworkConstants{

    public File abspath=new File(EXCEL_PATH);
    public Workbook workbook = null;


    /**
     * This method is used to read the numeric data from the excel in the form of double
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @param rowNum the row number in which the data is present
     * @param cellNum the actual cell number in which the numeric data is present
     * @return the data in double format
     */
    public double readNumberDataFromExcel(String sheetName,int rowNum,int cellNum) {
        try {
            FileInputStream fis=new FileInputStream(abspath);
            workbook = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getNumericCellValue();
    }

    /**
     * This method is used to read the boolean data from the excel in the form of boolean
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @param rowNum the row number in which the data is present
     * @param cellNum the actual cell number in which the boolean data is present
     * @return the data in boolean format
     */
    public boolean readBooleanDataFromExcel(String sheetName,int rowNum,int cellNum) {
        try {
            FileInputStream fis=new FileInputStream(abspath);
            workbook = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getBooleanCellValue();
    }

    /**
     * This method is used to read the String data from the excel in the form of String
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @param rowNum the row number in which the data is present
     * @param cellNum the actual cell number in which the String data is present
     * @return the data in String format
     */
    public String readStringDataFromExcel(String sheetName,int rowNum,int cellNum) {
        try {
            FileInputStream fis=new FileInputStream(abspath);
            workbook = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
    }

    /**
     * This method is used to read the Date value from the excel in the form of Date
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @param rowNum the row number in which the data is present
     * @param cellNum the actual cell number in which the Date value is present
     * @return the data in Date format
     */
    public Date readDateValueFromExcel(String sheetName,int rowNum,int cellNum) {
        try {
            FileInputStream fis=new FileInputStream(abspath);
            workbook = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getDateCellValue();
    }

    /**
     * This method is used to read any data present in the excel in the form of String
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @param rowNum the row number in which the data is present
     * @param cellNum the actual cell number in which the data is present
     * @return the data in String format
     */
    public String readAnyDataFromExcelInString(String sheetName,int rowNum,int cellNum) {
        File abspath=new File(EXCEL_PATH);
        Workbook workbook = null;
        try {
            FileInputStream fis=new FileInputStream(abspath);
            workbook = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).toString();
    }

    /**
     * This method is used to read all the data present in the excel in the form of String
     * @param filePath the path of the Excel file along with file name and extension
     * @param sheetName the name of the sheet in which we have to read the data
     * @return all the data present in the Excel in the form of String [][]
     */
    public String[][] getMultipleData(String sheetName) {
        return getMultipleData(EXCEL_PATH, sheetName);
    }

    public String[][] getMultipleData(String filePath, String sheetName) {
        File absolutePath = new File(filePath);
        try (FileInputStream fis = new FileInputStream(absolutePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            int physicalRowCount = sheet.getPhysicalNumberOfRows();
            int physicalCellCount = sheet.getRow(0).getPhysicalNumberOfCells();

            String[][] dataArray = new String[physicalRowCount][physicalCellCount];
            DataFormatter dataFormatter = new DataFormatter();
            for (int i = 0; i < physicalRowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < physicalCellCount; j++) {
                    Cell cell = row == null ? null : row.getCell(j);
                    dataArray[i][j] = cell == null ? "" : dataFormatter.formatCellValue(cell);
                }
            }
            return dataArray;
        } catch (EncryptedDocumentException | IOException exception) {
            throw new RuntimeException(
                    "Unable to read Excel data from file: " + filePath + ", sheet: " + sheetName,
                    exception);
        }
    }

}

