package com.snpm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ini4j.Ini;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.snpm.operation.Action;
import com.snpm.operation.TestCase;
import com.snpm.test.TestBase;

public class BaseUtil {
	public static final String reportHeadline = "SNPM TEST REPORT";
	//private static ArrayList<Action> actions = TestCase.actions;
	private Ini pageIni = TestBase.pageIni;
	
	public static ExtentReports getReportInstance() {
		ExtentReports reports = new ExtentReports("testreport\\report_"  + Util.getDate() + "_" + Util.getTime()+ ".html");
		//reports.config().reportHeadline(reportHeadline);
		//reports.config().documentTitle("Automation Report").reportName("Smoke");

		//Load report config
		reports.loadConfig(ExtentReports.class, "extent-config.xml");
		//adding system info
		Map<String, String> sysInfo = new HashMap<String, String>();
		sysInfo.put("Environment", (String) getProperty().get("env"));
		sysInfo.put("Browser", (String) getProperty().get("browser"));
		sysInfo.put("URL", (String) getProperty().get("homepage.url."+getProperty().get("env")));
		

		reports.addSystemInfo(sysInfo);
		return reports;
	}

	private static int findRow(Sheet sheet, String cellContent) {
		//System.out.println("0. &&&&&&&&&&&&&& --- cellContent value = " + cellContent);
	    for (Row row : sheet) {
	        for (Cell cell : row) {
	            //System.out.println("1. &&&&&&&&&&&&&& --- value = " + cell.getRichStringCellValue().getString().trim());
	        	if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	        		// System.out.println("2. &&&&&&&&&&&&&& --- value = " + cell.getRichStringCellValue().getString().trim());
	                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
	                    return row.getRowNum();  
	                }
	            }
	        }
	    }               
	    return 0;
	}
	
	// Given a start Row number to find next non empty row number
	private static int findNextNonEmptyRow(Sheet sheet, int startRowNum) {
		//System.out.println("findNextNonEmptyRow:: &&&&&&&&&&&&&& --- startRowNum = " + startRowNum);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		//System.out.println("findNextNonEmptyRow:: &&&&&&&&&&&&&& --- rowCount = " + rowCount);
		int nextRowNum = 0;

		for (int i = 1; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(startRowNum + i);
			//System.out.println("------------- row number = " + (startRowNum + i) + "-------------------" );
			//System.out.println("-------------1. value of first cell :  [" + row.getCell(0).toString() + "]-----length : " + row.getCell(0).toString().length() );
			//Check if the first cell contain a value, if yes, That means it is the new testcase name
		    if ( row.getCell(0) == null ) {//First cell empty 
		    	nextRowNum = startRowNum + i; 
		    } else if (row.getCell(0).toString().length() == 0) {//Still empty
		       	nextRowNum = startRowNum + i;
		    } else {//Non-empty, another test case
		       	break;
	        }
			//System.out.println("------------- next row number = " + nextRowNum + "-------------------" );
		}
		return nextRowNum;
	}
	
	public static String CaptureScreen(WebDriver driver, String ImagesPath) {
		TakesScreenshot oScn = (TakesScreenshot) driver;
		File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
		File oDest = new File(ImagesPath + ".jpg");
		try {
			FileUtils.copyFile(oScnShot, oDest);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ImagesPath + ".jpg";
	}


	public static Properties getProperty() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config/config.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static Sheet readExcel(String filePath, String fileName, String sheetName) throws IOException {
		// Create a object of File class to open xlsx file
		File file = new File(filePath + "\\" + fileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;
		// Find the file extension by spliting file name in substing and getting
		// only extension name
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(inputStream);
		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of XSSFWorkbook class
			workbook = new HSSFWorkbook(inputStream);
		}
		// Read sheet inside the workbook by its name
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet;
	}

	@DataProvider(name = "ReportTestCasesExcelData" ) 
	public static Object[][] getDataFromDataprovider(Method method) throws IOException {
		Object[][] object = null;
		Properties properties = getProperty();
		// Read keyword sheet
		Sheet sheet = readExcel("masterdata\\" + properties.getProperty("environment") + "\\",
				method.getName()+".xlsx", "Sheet1");
		// Find number of rows in excel file
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		object = new Object[rowCount][5];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Print excel data in console
				object[i][j] = row.getCell(j).toString();
			}
		}
		return object;
	}

	@DataProvider(name = "POMReportTestCasesExcelData" ) 
	public static Object[][] getPOMDataFromDataprovider(Method method) throws IOException {
		Object[][] object = null;
		Properties properties = getProperty();
		// Read keyword sheet
		System.out.println("Reading file from :"+"masterdata\\" + properties.getProperty("environment") + "\\"+
				method.getName()+".xlsx");
		Sheet sheet = readExcel("masterdata\\" + properties.getProperty("environment") + "\\",
				method.getName()+".xlsx", "Sheet1");
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		object = new Object[rowCount][4];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Print excel data in console
				Cell cellValue = row.getCell(j);
				if (cellValue == null) {
					object[i][j] = "";
				} else {
					object[i][j] = row.getCell(j).toString();
				}
			}
		}
		return object;
	}
	
	@DataProvider(name="CheckInputsExcelData")
	public static Object[][] getCheckInputsExcelData(String tc) throws IOException {
		Object[][] object = null;
		Properties properties = getProperty();
		// Read keyword sheet
		System.out.println("Reading file from :"+"masterdata\\" + properties.getProperty("environment") + "\\"+
				"verirfyLTSSAVCUsageAccessSeeker"+".xlsx");
		Sheet sheet = readExcel("masterdata\\" + properties.getProperty("environment") + "\\",
				"verirfyLTSSAVCUsageAccessSeeker"+".xlsx", "Sheet1");
		int startRow = findRow(sheet, tc);
		// Find number of rows in excel file
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		
		object = new Object[rowCount][4];
		outerloop:
		for (int i = 1; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(startRow + i);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				//Check if the first cell contain a value, if yes, That means it is the new testcase name
		        if(row.getCell(0).toString().length()==0){
		        	// Print excel data in console
		        	System.out.println(row.getCell(2).toString()+"----"+ row.getCell(3).toString()+"----"+
		                 row.getCell(5).toString()+"----"+ row.getCell(6).toString());
		        	// Read actural started from third column
		        	int k = j + 2;
		        	Cell cellValue = row.getCell(k);
		        	if (cellValue == null) {
		        		object[i][k] = "";
		        	} else {
		        		object[i][k] = row.getCell(j).toString();
		        	}
		        } else { // Another new test case, break out
		        	break outerloop;
		        }
			}
		}
		return object;
	}

	//@DataProvider(name="clickPageLinkExcelData")
	public static Object[][] clickPageLinkExcelData(String tcName) throws IOException {
		Object[][] object = null;
		Properties properties = getProperty();
		// Read keyword sheet
		//System.out.println("Reading file from :"+"masterdata\\" + properties.getProperty("environment") + "\\"+
			//	"verifyHomePage"+".xlsx");
		
		Sheet sheet = readExcel("masterdata\\" + properties.getProperty("environment") + "\\",
				"verifyHomePage"+".xlsx", "Sheet1");
		int startRow = findRow(sheet, tcName);
		int nextRow = findNextNonEmptyRow(sheet, startRow);
		//System.out.println("~~~~~~~~~~~~~~ test case : " + tcName);
		//System.out.println("~~~~~~~~~~~~~~ start row : " + startRow);
		//System.out.println("~~~~~~~~~~~~~~ next row : " + nextRow);
		
		// Find number of rows in excel file
		// Find number of rows in excel file
		//int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		int rowCount = nextRow - startRow;
		object = new Object[rowCount][5];
		outerloop:
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(startRow + i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0, k=j+2 ; k < row.getLastCellNum(); j++, k++) {
				//Check if the first cell contain a value, if yes, That means it is the new testcase name
		        if(row.getCell(0).toString().length()==0){
		        	// Print excel data in console
		        	//System.out.println(row.getCell(2).toString()+"----"+ row.getCell(3).toString()+"----"+
		              //   row.getCell(5).toString()+"----"+ row.getCell(6).toString() + "----[" + row.getFirstCellNum() + "]---" + row.getLastCellNum());
       	
		        	// Read actural started from third column
		        	//int k = j + 2;
		        	Cell cellValue = row.getCell(k);
		        	if (cellValue == null) {
		        		object[i][j] = "";
		        	} else {
		        		//System.out.println("---k-----" + k + "--j--" + j + "------[" +row.getCell(k).toString() + "]---------" + row.getLastCellNum());
		        		object[i][j] = row.getCell(k).toString();
		        		
		        	}
		        } else { // Another new test case, break out
		        	break outerloop;
		        }
			}
		}
		return object;
	}
	
	public TestCase findTcByPageName(String pageName, String tcName) throws IOException {
		Row row = null;
		TestCase tc = new TestCase();
		//System.out.println("++++++++ pageName: " + pageName +  "+++++tcName = " + tcName);
		
		//Get dataSource related to this pageName
		String dataSource = pageIni.get(pageName).get("dataSource");
		Sheet sheet = readExcel("masterdata\\", dataSource +".xlsx", "Sheet1");
		
		int startRow = findRow(sheet, tcName);
		//System.out.println("++++++++ start row number: " + startRow );
		//Get test case name, add it to TestCase class
		row = sheet.getRow(startRow);
		tc.setTcName((row.getCell(0) == null ? "" : row.getCell(0).toString()));
		tc.setTcDesc((row.getCell(1) == null ? "" : row.getCell(1).toString()));
		//System.out.println("---- Test Case Desc: " + row.getCell(1).toString()+"----"); 
		
		int nextRow = findNextNonEmptyRow(sheet, startRow);
		int rowCount = nextRow - startRow;
		//System.out.println("++++++++ next row number: " + nextRow + "######## Row count: " + rowCount );
		
		for (int i = 0; i < rowCount; i++) {
			Action action = new Action();
			//System.out.println("++++++++ row number: " + (startRow + i) + "++++++++");
			// Loop over all the rows
			row = sheet.getRow(startRow + i + 1);
	    	//System.out.println(row.getCell(2).toString()+"----"+ row.getCell(3).toString()+"----["+ row.getCell(4).toString() + "]----" +
	    			//row.getCell(5).toString()+"----"+ row.getCell(6).toString() + "----[" + row.getFirstCellNum() + "]---" + row.getLastCellNum());
       	   	//Test scenarios started from third column
		    //Fill in Action object
		    action.setTcName(row.getCell(2) == null ? "" : row.getCell(2).toString());
		    action.setOperation(row.getCell(3) == null ? "" : row.getCell(3).toString());
		    action.setObjName(row.getCell(4) == null ? "" : row.getCell(4).toString());
		    action.setObjType(row.getCell(5) == null ? "" : row.getCell(5).toString());
		    action.setValue(row.getCell(6) == null ? "" : row.getCell(6).toString());
		    tc.addAction(action);
		    //System.out.println("@@@@@@@@Action list size is : " + tc.getActions().size());
		}
		return tc;
	}
	
	@DataProvider(name="LoginTestExcelData")
	public static Object[][] getLoginTestExcelData(Method m) throws IOException {
		System.out.println("inside");
		Object[][] object = null;
		
		Sheet sheet = readExcel("masterdata\\", "LoginPage"+".xlsx", "Sheet1");
		int startRow = findRow(sheet, "login");
		int nextRow = findNextNonEmptyRow(sheet, startRow);

		int rowCount = nextRow - startRow;
		object = new Object[rowCount][2];
		outerloop:
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = sheet.getRow(startRow + i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0, k=j+1 ; k < row.getLastCellNum(); j++, k++) {
				//Check if the first cell contain a value, if yes, That means it is the new testcase name
		        if(row.getCell(0) == null || row.getCell(0).toString().length()==0){
		        	//Read actually starts from second column
		        	Cell cellValue = row.getCell(k);
		        	if (cellValue == null) {
		        		object[i][j] = "";
		        	} else {
		        		System.out.println("---k-----" + k + "--j--" + j + "------[" +row.getCell(k).toString() + "]---------" + row.getLastCellNum());
		        		object[i][j] = row.getCell(k).toString();
		        		
		        	}
		        } else { // Another new test case, break out
		        	break outerloop;
		        }
			}
		}
		return object;
	}
	
	/**
	 * create file and return file handler object
	 * @return 
	 */
	public static FileHandler getFileHandler() {
		FileHandler fh = null;
		final String filePath = "log\\SNPM_log.txt";
		try {
			fh = new FileHandler(filePath);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      fh.setFormatter(new SimpleFormatter());
	      return fh;
	}
	
}