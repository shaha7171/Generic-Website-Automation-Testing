package com.snpm.test;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.snpm.util.BaseUtil;

public class TestHomePage extends TestBase {
	private ExtentTest test;
	private final String pageName = "[GenericPageName]: ";

	// TC2 : Test if Date input field is present
	@Test(priority = 1)
	public void enterInputs() throws Exception {

		test = reports.startTest(pageName + "Sample test to validate Webpage title");
		if (homePage.validateTitle(test)) {
			test.log(LogStatus.PASS, "Text is Successfully inserted.");
			BaseUtil.CaptureScreen(driver,passScreenShotPath + this.getClass().getSimpleName().replaceAll("Test", "") + "//isTitleCorrect");
		} else {
			BaseUtil.CaptureScreen(driver,failScreenShotPath + this.getClass().getSimpleName().replaceAll("Test", "") + "//isTitleCorrect");
			test.log(LogStatus.FAIL, "Text is not Successfully inserted.");
		}
		reports.endTest(test);
		// Thread.sleep(5000);
	}

}