package com.snpm.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.snpm.test.TestBase;

public class ChromeWebDriver implements Webdriver {
	private WebDriver driver;
	public ChromeWebDriver() {
		// TODO Auto-generated constructor stub
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setBinary(
				TestBase.getProp().getProperty("chromepath"));
		//"C://Program Files (x86)//Google//Chrome//Application//chrome.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
	public WebDriver getDriver() {
		return driver;
	}
}
