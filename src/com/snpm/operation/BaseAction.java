/**
 * 
 */
package com.snpm.operation;

import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.snpm.test.TestBase;

/**
 * @author ashutoshbkumar
 *
 */

public class BaseAction {
	protected static final Logger logger = TestBase.logger;
	//private static final String CLASSNAME = BaseAction.class.getName();

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @param test 
	 * @throws Exception
	 */
	public boolean checkElementText(WebDriver driver, Properties p, String objectName, String objectType, String value, ExtentTest test) {
		try {
			String actualtext = driver.findElement(this.getObject(p, objectName, objectType)).getText();
			if (actualtext.equals(value)) {
				test.log(LogStatus.INFO,"Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				logger.info("Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				return true;
			} else {
				test.log(LogStatus.INFO,"Expected text is not matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				logger.error("Expected text is not matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Expected text output:"+value+". Unable to get the actual element from UI. Please refer to logs for more details.");
			logger.fatal(e.getMessage());
			return false;
		}
	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @throws Exception
	 */
	public boolean checkHiddenText(WebDriver driver, Properties p, String objectName, String objectType, String value,ExtentTest test) {
		try {
			String actualtext = driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("textContent");
			if (actualtext.equals(value)) {
				test.log(LogStatus.INFO,"Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				logger.info("Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				return true;
			} else {
				test.log(LogStatus.INFO,"Expected text is not matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				logger.error("Expected text is not matching actual text. Expected text output:"+value+". Actual text in Report UI:"+actualtext);
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Expected text output:"+value+". Unable to get the actual element from UI. Please refer to logs for more details.");
			logger.fatal(e.getMessage());
			return false;
		}
	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean checkElements(WebDriver driver, Properties p, String objectName, String objectType) {
		boolean isDisplayed = true;
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(this.getObject(p, objectName, objectType));
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}
		int size = elements.size();
		for (int i = 0; i < size; i++) {

			if (!elements.get(i).isDisplayed()) {
				isDisplayed = false;
				break;
			}
		}

		if (isDisplayed) {
			logger.info( objectName + " is present on webpage");
			return true;
		} else {
			logger.error( objectName + " is not present on webpage");
			return false;
		}
	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean checkElement(WebDriver driver, Properties p, String objectName, String objectType) {
		try {
			if (driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed()) {
				logger.info(objectName + " is present on webpage");
				return true;
			} else {
				logger.error(objectName + " is not present on webpage");
				return false;
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}
	}
	
	public boolean checkInputErrorMsg(WebDriver driver, Properties p, String objectName, String objectType, String value,ExtentTest test) {
		try {
			if (driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("title").equals(value) | driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("original-title").equals(value)) {
				test.log(LogStatus.INFO,"Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("title")
						+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("original-title"));
				logger.info("Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("title")
						+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("original-title"));
				return true;
			} else {
				test.log(LogStatus.INFO,"Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("title")
						+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("original-title"));
				logger.error("Expected text is matching actual text. Expected text output:"+value+". Actual text in Report UI:"+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("title")
						+driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("original-title"));
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Expected text output:"+value+". Unable to get the actual element from UI. Please refer to logs for more details.");
			logger.fatal(e.getMessage());
			return false;
		}
	}
	

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean getText(WebDriver driver, Properties p, String objectName, String objectType) {
		try {
			driver.findElement(this.getObject(p, objectName, objectType)).getText();
			logger.info("get text: " + driver.findElement(this.getObject(p, objectName, objectType)).getText());
			return true;
		} catch (Exception e) {
			logger.fatal( e.getMessage());
			return false;
		}

	}

	/**
	 * @param p
	 * @param value
	 */
	public void goToUrl(WebDriver driver, Properties p, String value) {
		driver.get(p.getProperty(value));
		String titleName = driver.getTitle();
		logger.debug("Open URL : " + p.getProperty(value) + "#### title name: " + titleName);
	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean setText(WebDriver driver, Properties p, String objectName, String objectType, String value) {
		try {
			driver.findElement(this.getObject(p, objectName, objectType)).sendKeys(value);
			logger.info("Text is set in object");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}
	
	
	
	public boolean clearAndSetText(WebDriver driver, Properties p, String objectName, String objectType, String value) {
		try {
			WebElement ele = driver.findElement(this.getObject(p, objectName, objectType));
			ele.clear();
			ele.sendKeys(value);
			logger.info("Text is set in object");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean click(WebDriver driver, Properties p, String objectName, String objectType) {
		try {
			driver.findElement(this.getObject(p, objectName, objectType)).click();
			logger.info( "Button is cliked");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}
	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean validateTitle(WebDriver driver, Properties p, String objectName, String objectType, String value,ExtentTest test) {
		try {
			String actualtext = driver.getTitle();
			if (actualtext.equals(value)) {
				test.log(LogStatus.INFO,"Expected Title is matching actual title. Expected Title:"+value+". Actual Title:"+actualtext);
				logger.info("xpected Title is matching actual title. Expected Title:"+value+". Actual Title:"+actualtext);
				return true;
			} else {
				test.log(LogStatus.INFO,"Expected Title is not matching actual title. Expected Title:"+value+". Actual Title:"+actualtext);
				logger.info("xpected Title is not matching actual title. Expected Title:"+value+". Actual Title:"+actualtext);
				return false;
			}
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Expected text output:"+value+". Unable to get the title. Please refer to logs for more details.");
			logger.fatal(e.getMessage());
			return false;
		}
	}
	/* 
	 * Wait until element come up or timeout
	 */
	public boolean wait4Element(WebDriver driver, Properties p, String objectName, String objectType, String value) {
		try {
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setParseIntegerOnly(true);
			WebDriverWait wait = new WebDriverWait(driver, formatter.parse(value).intValue()); 
			logger.info( "Waiting for element to come up...");
			wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			logger.info( "Element has come up...");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}
	
	/* 
	 * Wait until element to be clickable or timeout
	 */
	public boolean wait4ElementClickable(WebDriver driver, Properties p, String objectName, String objectType, String value) {
		try {
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setParseIntegerOnly(true);
			WebDriverWait wait = new WebDriverWait(driver, formatter.parse(value).intValue()); 
			logger.info( "Waiting for element to be clickable...");
			logger.info("**** element located: " + driver.findElement(this.getObject(p, objectName, objectType)).getAttribute("href"));
			wait.until(ExpectedConditions.elementToBeClickable(this.getObject(p, objectName, objectType)));
			logger.info( "Element is clickable...");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}
	
	public boolean waitFor(String value) {
		try {
			//Implicit wait for some time
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setParseIntegerOnly(true);
            int waitForMilliSeconds = formatter.parse(value).intValue();
            logger.info( "Waiting for ..." + waitForMilliSeconds + " milli-seconds" );
            Thread.sleep(waitForMilliSeconds);
			logger.info( "Waiting for time ended...");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}
	}
	

	/**
	 * Find element BY using object type and value
	 * 
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	protected By getObject(Properties p, String objectName, String objectType) throws Exception {
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(objectName);
		}
		// find by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(objectName);

		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(objectName);

		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) {

			return By.cssSelector(objectName);

		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(objectName);

		}
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {

			return By.partialLinkText(objectName);

		} // find by partial link
		else if (objectType.equalsIgnoreCase("ID")) {

			return By.id(objectName);

		} else {
			throw new Exception("Wrong object type");
		}
	}

}