package com.snpm.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.snpm.page.BasePage;
import com.snpm.page.HomePage;
import com.snpm.util.BaseUtil;
import com.snpm.util.Util;
import com.snpm.webdriver.WebDriverFactory;

public class TestBase {

	public static final String FILENAME = "config/page.ini";
	public static Ini pageIni = getPageIni();
	public static Map<String, String> env = System.getenv();
	public static Properties prop = getProp();
	public static WebDriver driver = WebDriverFactory.getWebDriver(prop.getProperty("browser")).getDriver();
	protected ExtentTest LOGR;
	protected HomePage homePage;
	public static final ExtentReports reports = BaseUtil.getReportInstance();
	public static final Logger logger = LogManager.getLogger();

	public static String passScreenShotPath = null;
	public static String failScreenShotPath = null;
	public static  String WarningScreenShotPath = null;
	public static String report_url = null;
	
	public static final String workingDir = System.getProperty("user.dir");

	public static Properties getProp() {
		return BaseUtil.getProperty();
	}

	private static Ini getPageIni() {
		Ini ini = null;
		try {
			ini = new Ini(new FileReader(FILENAME));
		} catch (IOException ex) {
			// logger.log(Level.SEVERE, ex.toString(), ex);
			logger.fatal(ex.toString());
		}
		return ini;
	}

	public TestBase() {

	}
	@BeforeClass
	public void init() {
		final String methodName = "init";
		logger.entry(methodName);
			logger.debug("working directory: "+workingDir);
			passScreenShotPath = workingDir+"\\screenshot\\screenshot_" + Util.getDate() + "_" + Util.getTime() + "\\pass\\";
			failScreenShotPath = workingDir+"\\screenshot\\screenshot_" + Util.getDate() + "_" + Util.getTime() + "\\fail\\";
			WarningScreenShotPath = workingDir+"\\screenshot\\screenshot_" + Util.getDate() + "_" + Util.getTime() + "\\warning\\";
			report_url = workingDir+"\\testreport\\report_"  + Util.getDate() + "_" + Util.getTime()+ ".html";

		// Initialize all the pages
		logger.info("Intializing all the pages");
		BasePage.initPages();

			// get a HomePage instance
			logger.info("--- loadHomePage ---");
			homePage = BasePage.getHomePage(false);
		logger.exit();
	}

	@AfterTest
	public void close() throws Exception {
		String packageName = this.getClass().getPackage().toString();
		logger.info("!!!!!! Package name is : " + packageName);
			reports.flush();
			reports.close();
			// driver.quit();
			
			driver.get(report_url);
			if(prop.getProperty("sendemail").equals("Y")){
				Util.sendEmail();
			}else{
				logger.debug("sending email is not configured.");
			}
		
	}
}