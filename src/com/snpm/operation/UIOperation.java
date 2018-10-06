package com.snpm.operation;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.snpm.test.TestBase;
import com.snpm.util.AESCrypt;

public class UIOperation extends CustomAction{
	/* 
	 * Attributes
	 */
	private WebDriver driver;
	private boolean rc = true;
	private Properties p = TestBase.prop;
	
	public UIOperation() {}
	
	public UIOperation(WebDriver driver) {
		this.driver = driver;
	}

	public boolean runTestCase(TestCase tc,ExtentTest test) {

		ArrayList<Action> actions = tc.getActions();
		//System.out.println("%%%%% UIOperation : action list size : " + actions.size());
		//If Empty action gets passed, simply log an error and return false
		if ( actions.size() == 0 ) {
			logger.error("No action gets passed!");
			return false;
		}
		
		for (int i = 0 ; i < actions.size(); i++) {
			String ts = tc.getAction(i).getTcName();
			String op = tc.getAction(i).getOperation();
			String objName = tc.getAction(i).getObjName();
			String objType = tc.getAction(i).getObjType();
			String value = null;
			//if object name is password , decrypt the encrypted value
			if(objName.equals("password")){
			value = AESCrypt.decrypt(tc.getAction(i).getValue());
			}
			else if(objName.contains("int")){
				value = String.valueOf(new Double(tc.getAction(i).getValue()).intValue());
			}
			else{
			value = tc.getAction(i).getValue() ;
			}
			//System.out.println("^^^^^ TestCase Desc: " + tc.getTcDesc());
			//System.out.println(i + " :@@@@"+ ts + "@@@@" + op + "@@@" + objName + "@@@@" + objType + "@@@@@" + value );
			try {
				rc = runOneAction(ts,op,objName, objType, value,test);
				if(rc == false){
					return rc;
				}
			} catch (Exception ex){
				logger.fatal(ex.toString(), ex);
			}
		}
		return rc;
	}
	
	public boolean runOneAction(String testcasename, String operation, String objectName, String objectType,
			String value,ExtentTest test) throws Exception {
		final String methodName = "perform";
		logger.entry( methodName);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		logger.debug("--" + testcasename + "---" + operation + "--" + objectName + "----" + objectType + "---" + value + "----");
		try {
			switch (operation.toUpperCase()) {
			case "VALIDATETITLE":
				rc = validateTitle(driver, p, objectName, objectType, value, test);
				break;
			case "CLICK":
				// Perform click
				rc = click(driver, p, objectName, objectType);
				break;
			case "SETTEXT":
				// Set text on control
				rc = setText(driver, p, objectName, objectType, value);
				break;
			case "CLEARANDSETTEXT":	
				// clear the text and set the value
				rc = clearAndSetText(driver, p, objectName, objectType, value);
				break;
			case "GOTOURL":
				// Get url of application
				//rc = goToUrl(driver, p, value);
				break;
			case "GETTEXT":
				// Get text of an element
				rc = getText(driver,p, objectName, objectType);
				break;
			case "CHECKELEMENT":
				// Check if element is present
				rc = checkElement(driver, p, objectName, objectType);
				break;
			case "CHECKINPUTERRORMSG":
				// Check if input error message is correct
				rc = checkInputErrorMsg(driver, p, objectName, objectType,value,test);
				break;
			case "CHECKELEMENTS":
				// Check if elements is present
				rc = checkElements(driver, p, objectName, objectType);
				break;
			case "CHECKELEMENTTEXT":
				// Check if element is present. If yes, check if element text is
				// matching.
				rc = checkElementText(driver, p, objectName, objectType, value,test);
				break;
			case "CHECKHIDDENTEXT":
				// Check if element is present. If yes, check if element text is
				// matching.
				rc = checkHiddenText(driver, p, objectName, objectType, value,test);
				break;
			case "CHECKMANDATORY":
				// Check if input is mandatory
				rc = checkMandatory(driver, p, objectName, objectType, value);
				break;
			case "MOUSEHOVER":
				//Check text on mousehover
				rc = mouseHover(driver, p, objectName, objectType);
				break;
			case "WAIT4ELEMENT":
				//Check text on mousehover
				rc = wait4Element(driver, p, objectName, objectType, value);
				break;
			case "WAIT4ELEMENTCLICKABLE":
				//Check text on mousehover
				rc = wait4ElementClickable(driver, p, objectName, objectType, value);
				break;
			case "WAITFOR":
				//Check text on mousehover
				rc = waitFor(value);
				break;
			default:
				break;
			}
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		}
		logger.exit( methodName);
		return rc;
	}
}