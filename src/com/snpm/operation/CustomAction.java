/**
 * 
 */
package com.snpm.operation;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * @author ashutoshbkumar
 *
 */
public class CustomAction extends BaseAction {

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @param value
	 * @throws Exception
	 */
	public boolean checkMandatory(WebDriver driver, Properties p, String objectName, String objectType, String value) {
		try {
			if (driver.findElement(getObject(p, objectName, objectType)).getAttribute("class").contains("optional")) {
				logger.error(value + " is not mandatory input");
				return false;
			} else {
				logger.info("It is mandatory input");
				return true;
			}
		} catch (Exception e) {
			logger.fatal( e.getMessage());
			return false;
		}
	}

	/**
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @throws Exception
	 */
	public boolean mouseHover(WebDriver driver, Properties p, String objectName, String objectType) {
		Actions action;
		action = new Actions(driver);
		try {
			action.moveToElement(driver.findElement(this.getObject(p, objectName, objectType)));
			action.perform();
			logger.info("Mouse moved/hovered to specified element");
			return true;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		}

	}

}
