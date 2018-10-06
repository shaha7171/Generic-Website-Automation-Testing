/**
 * 
 */
package com.snpm.page;

import org.apache.logging.log4j.Logger;
import com.snpm.test.TestBase;

/**
 * @author jasonzhang
 *
 */
public class PageFactory {
	/**
	 * Attributes
	 */
	private static BasePage basePage;
	private static Logger logger = TestBase.logger;

	public static BasePage getPage(String pageName, boolean isAccessSeeker){
		final String methodName = "getPage";
		logger.entry(methodName);
		try {
			//jLogger.log(Level.INFO, "!!!!!!!! new instance: {0} ", new Object[] {Class.forName("com.snpm.page."+pageName).getConstructor(String.class).newInstance(pageName)});
			if (isAccessSeeker) {
				logger.debug("!!!!!!!! new instance: {0} ", new Object[] {Class.forName("com.snpm.page.accessseeker."+pageName).getConstructor(String.class).newInstance(pageName)});
				basePage = (BasePage) Class.forName("com.snpm.page.accessseeker."+pageName).getConstructor(String.class).newInstance(pageName);
			} else {
				logger.debug("!!!!!!!! new instance: {0} ", new Object[] {Class.forName("com.snpm.page."+pageName).getConstructor(String.class).newInstance(pageName)});
				basePage = (BasePage) Class.forName("com.snpm.page."+pageName).getConstructor(String.class).newInstance(pageName);
			}
			logger.debug("---------- basePage {0} ++++++++++ ", new Object[] {basePage});
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		}
		return basePage;
	}
}