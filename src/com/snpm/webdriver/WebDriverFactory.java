package com.snpm.webdriver;

public class WebDriverFactory {
	public static Webdriver driver;
	public WebDriverFactory() {};
	public static Webdriver getWebDriver(String wd) {
		switch (wd) {
			case "firefox" : 
				driver = new FirefoxWebDriver();
				break;
			case "chrome": 
				driver = new ChromeWebDriver();
				break;
			default: 
				driver = new FirefoxWebDriver();
				break;
		}
		return driver;
	}
	
}
