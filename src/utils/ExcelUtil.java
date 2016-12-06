package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class ExcelUtil implements Iterator<Object[]> {

	private Workbook dataWorkbook;
	private Sheet dataFirstSheet;
	private Iterator<Row> iterator;
	private Row nextRow;
	private Iterator<Cell> cellIterator;
	private Cell nextCell;
	
	private static Workbook resultWorkbook;
	private static Sheet resultWorkingSheet;
	
	public ExcelUtil(String dataPath, String resultPath) throws IOException {
		FileInputStream dataInputStream = new FileInputStream(new File(dataPath));
		dataWorkbook = getWorkbook(dataInputStream, dataPath);
		dataFirstSheet = dataWorkbook.getSheetAt(0);
	    iterator = dataFirstSheet.iterator();
	    
	    FileInputStream resultInputStream = new FileInputStream(new File(resultPath));
		resultWorkbook = getWorkbook(resultInputStream, resultPath);
		Sheet resultLastSheet = resultWorkbook.getSheetAt(resultWorkbook.getNumberOfSheets() - 1);
		if (resultLastSheet.getPhysicalNumberOfRows() == 0) {
			resultWorkingSheet = resultLastSheet;
		}
		else {
			resultWorkingSheet = resultWorkbook.createSheet("Sheet" + (resultWorkbook.getNumberOfSheets() + 1));
		}
		Row resultTitleRow = resultWorkingSheet.createRow(0);
		Cell caseNameTitleCell = resultTitleRow.createCell(0);
		caseNameTitleCell.setCellType(CellType.STRING);
		caseNameTitleCell.setCellValue("Case");
		Cell queryTitleCell = resultTitleRow.createCell(1);
		queryTitleCell.setCellType(CellType.STRING);
		queryTitleCell.setCellValue("Query");
		for (int i = 1; i <= 8; i++) {
			Cell resultItemTitleCell = resultTitleRow.createCell(i + 1);
			resultItemTitleCell.setCellType(CellType.STRING);
			resultItemTitleCell.setCellValue("Result" + i);
		}
		FileOutputStream fileOut = new FileOutputStream(resultPath);
		resultWorkbook.write(fileOut);
	    fileOut.close();
	}
	
	private static Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
	    Workbook workbook = null;
	    if (excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    }
	    else if (excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    }
	    else {
	        throw new IllegalArgumentException("The specified file is not excel file");
	    }
	    return workbook;
	}
	
	public static void writeResultToExcelFile(String resultPath, TestData testData, List<WebElement> elements) throws IOException {
		Row row = resultWorkingSheet.createRow(resultWorkingSheet.getPhysicalNumberOfRows());
		Cell caseNameCell = row.createCell(0);
		caseNameCell.setCellType(CellType.STRING);
		caseNameCell.setCellValue(testData.getCaseName());
		Cell queryCell = row.createCell(1);
		queryCell.setCellType(CellType.STRING);
		queryCell.setCellValue(testData.getQuery());
		int i = 2;
		for (WebElement element : elements) {
			Cell resultItemCell = row.createCell(i++);
			resultItemCell.setCellType(CellType.STRING);
			resultItemCell.setCellValue(element.getText());
		}
		FileOutputStream fileOut = new FileOutputStream(resultPath);
		resultWorkbook.write(fileOut);
	    fileOut.close();
	}
	
	public static List<TestData> readTestDataFromExcelFile(String excelFilePath) throws IOException {
		List<TestData> testDataList = new ArrayList<TestData>();
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	    while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            TestData testData = new TestData();
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();
                switch (columnIndex) {
                case 1:
                	testData.setQuery(nextCell.getStringCellValue());
                    break;
                case 2:
                	testData.setExpectedFirstCard(nextCell.getStringCellValue());
                    break;
                }
            }
            testDataList.add(testData);
        }
	    workbook.close();
	    inputStream.close();
	    return testDataList;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return iterator.hasNext();
	}

	@Override
	public Object[] next() {
		// TODO Auto-generated method stub
		if (iterator.hasNext()) {
			nextRow = iterator.next();
			cellIterator = nextRow.cellIterator();
			TestData testData = new TestData();
			while (cellIterator.hasNext()) {
                nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();
                switch (columnIndex) {
                case 0:
                	testData.setCaseName(nextCell.getStringCellValue());
                	break;
                case 1:
                	testData.setQuery(nextCell.getStringCellValue());
                    break;
                case 2:
                	testData.setExpectedFirstCard(nextCell.getStringCellValue());
                    break;
                }
            }
			Object[] object = new Object[1];
			object[0] = testData;
			return object;
		}
		return null;
	}
	
}
