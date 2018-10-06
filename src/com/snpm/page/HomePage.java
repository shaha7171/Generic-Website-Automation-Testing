/**
 * 
 */
package com.snpm.page;

import com.relevantcodes.extentreports.ExtentTest;
import com.snpm.operation.TestCase;
import com.snpm.operation.UIOperation;
import com.snpm.test.TestBase;

/**
 * @author jasonzhang
 *
 */
public class HomePage extends BasePage {
	/**
	 * Attributes
	 */
	private UIOperation operation = new UIOperation(TestBase.driver);
	private boolean retCode = true;
	private String pageName = "";

	public HomePage(String pageName) {
		this.pageName = pageName;
	}

	

	private boolean clickPageLink(String tcName) {
		logger.entry(tcName);
		// Try finding the click page link test case and then execute it
		try {
			TestCase tc = baseUtil.findTcByPageName(pageName, tcName);
			logger.info("clickPageLink: " + tc);
			retCode = operation.runTestCase(tc, null);
			if (retCode)
				logger.info("Report link is clicked");
			else
				logger.error("Report link is not clicked");

		} catch (Exception ex) {
			retCode = false;
			logger.fatal(ex.toString(), ex);
		}
		// reports.endTest(testcase);
		logger.exit(tcName);
		return retCode;
	}
	
	public boolean validateTitle(ExtentTest test) {
		if (validateGoogleTitle("validateTitle",test))
			return true;
		else
			return false;

	}
	
	public boolean validateGoogleTitle(String tcName, ExtentTest test) {
		logger.entry(tcName);
		// Try finding the click page link test case and then execute it
		try {
			TestCase tc = baseUtil.findTcByPageName(pageName, tcName);
			logger.info("validate title: " + tc);
			retCode = operation.runTestCase(tc, test);
			if (retCode)
				logger.info("Title is validated");
			else
				logger.error("Title is not validated");

		} catch (Exception ex) {
			retCode = false;
			logger.fatal(ex.toString(), ex);
		}
		// reports.endTest(testcase);
		logger.exit(tcName);
		return retCode;
	}
}