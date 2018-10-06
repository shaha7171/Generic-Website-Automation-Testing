package com.snpm.page;

import java.io.FileReader;
import java.util.Iterator;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.snpm.operation.UIOperation;
import com.snpm.test.TestBase;
import com.snpm.util.BaseUtil;
import com.snpm.util.Util;

public class BasePage {
	public static boolean LOGIN_FLAG = false;
	public static final String jsonFile = "config/searchers.json";
	public static final String reprtdetailsjsonFile = "config/reportdetails.json";
	public static final Logger logger = TestBase.logger;
	protected static Ini pageIni = TestBase.pageIni; 
	protected static JSONObject searchersJsonObj = initSearchers();
	protected static JSONObject reportDetailsJsonObj = initReportDetails();
	private static Properties prop = TestBase.prop;
	protected static WebDriver driver = TestBase.driver;
	protected UIOperation operation = new UIOperation(driver);
	protected BaseUtil baseUtil = new BaseUtil();
	protected Util util = new Util();
	private JSONObject searchers = null;
	private JSONObject reportdetails = null;
	
	//protected ExtentReports reports = BaseUtil.getReportInstance();
	protected ExtentReports reports = TestBase.reports;
		
	public static final String passScreenShotPath = System.getenv().get("HOME") + "\\git\\snpm_automation_framework\\snpm_automation_framework\\screenshot\\screenshot_"+Util.getDate()+"_"+Util.getTime()+"\\pass\\";
	public static final String failScreenShotPath = System.getenv().get("HOME") + "\\git\\snpm_automation_framework\\snpm_automation_framework\\screenshot\\screenshot_"+Util.getDate()+"_"+Util.getTime()+"\\fail\\";
	public static final String WarningScreenShotPath = System.getenv().get("HOME") + "\\git\\snpm_automation_framework\\snpm_automation_framework\\screenshot\\screenshot_"+Util.getDate()+"_"+Util.getTime()+"\\warning\\";
	
	public static Hashtable<String, BasePage> Pages = new Hashtable<String, BasePage>();

	public BasePage() {
		
	}
	
	private static JSONObject initSearchers() {
        JSONParser parser = new JSONParser();
        try {
        	System.out.print("*** inside initSearchers ****");
        	Object obj = parser.parse(new FileReader(jsonFile));
        	
        	 if (obj != null) {
             	logger.debug("*** searchers: " + obj);
        	 	System.out.println("**** searcher *****" + obj);
        	 } else {
             	logger.debug("**** null Searcher *****");
             	System.out.println("**** Null searcher *****");
        	 }

            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject != null)
            	logger.debug("*** searchers: " + jsonObject);
            else 
            	logger.debug("**** null Searcher *****");
            return jsonObject;
        } catch (Exception ex) {
        	logger.fatal(ex.getMessage());
        	ex.printStackTrace();
        }
        return null;
	}
	
	private static JSONObject initReportDetails(){
		JSONParser parser = new JSONParser();
        try {
        	System.out.print("*** init report details ****");
        	Object obj = parser.parse(new FileReader(reprtdetailsjsonFile));
        	
        	 if (obj != null) {
             	logger.debug("*** report details: " + obj);
        	 	System.out.println("**** report details *****" + obj);
        	 } else {
             	logger.debug("**** null report details *****");
             	System.out.println("**** Null report details *****");
        	 }

            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject != null)
            	logger.debug("*** report details: " + jsonObject);
            else 
            	logger.debug("**** null report details *****");
            return jsonObject;
        } catch (Exception ex) {
        	logger.fatal(ex.getMessage());
        	ex.printStackTrace();
        }
        return null;
		
	}
	
	protected BasePage navigateTo(String pageName) {
		return getPage(pageName);
	}
	
	public static void initPages() { // Methods to initialize all the pages
		// get a new page instance from PageFactory
		String methodName="initPages";
		logger.entry(methodName);
		logger.info("Intializing all the pages");

		for ( String key: pageIni.keySet()) {
			String pageType = pageIni.get(key).get("pageType");
			logger.debug("****** pageType : " + pageType);
			boolean isAccessSeeker = pageType.equals("SNPM_ACCESS_SEEKER") ? true:  false;
			//use page name(key) as the pages hash table key and also pass key to instantiate page
			logger.debug("----Add page: {0} " + new Object[] {key});
			logger.debug("!!!!!!key =" + key + "Page: " +PageFactory.getPage(key, isAccessSeeker));

			addPage(key, PageFactory.getPage(key, isAccessSeeker));
		}
		logger.exit(methodName);
	}

	public static void addPage(String pageName, BasePage page) {
		Pages.put(pageName, page);
	}

	public static BasePage getPage(String pageName) {
		return Pages.get(pageName);
	}
	
	public static HomePage getHomePage(boolean isAccessSeeker) {
		NavToHomePage(isAccessSeeker);
		return (HomePage) getPage("HomePage");
	}
	
	private static void NavToHomePage(boolean isAccessSeeker) {
		//Get home url and navigate to home
		String env = prop.getProperty("env");
		String homeUrl = "homepage.url" + "." + env;
		String envType = prop.getProperty(env);
		
		logger.debug("--------HomeUrl ------ " + homeUrl);
		driver.get(prop.getProperty(homeUrl));
		
	}

	public Object findSearcherByPageNameAndId(String pageName, String id) {
		searchers = (JSONObject) searchersJsonObj.get(pageName);
		System.out.println("**** BasePage searcher : " + searchers.get(id));
		return searchers.get(id);
	}
	
	public JSONObject findSearchersByPageName(String pageName) {
		return (JSONObject) searchersJsonObj.get(pageName);
	}
	
	public Object findReportDetailsByPageNameAndId(String reportName, String id) {
		reportdetails = (JSONObject) reportDetailsJsonObj.get(reportName);
		System.out.println("**** BasePage report details : " + reportdetails.get(id));
		return reportdetails.get(id);
	}
	
	public JSONObject findReportDetailsByPageName(String reportName) {
		return (JSONObject) reportDetailsJsonObj.get(reportName);
	}
}