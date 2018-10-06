package com.snpm.webdriver;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.snpm.test.TestBase;

public class FirefoxWebDriver implements Webdriver {
	private WebDriver driver;
	public FirefoxWebDriver() {
		
		FirefoxProfile profile=new FirefoxProfile();
		File pathToBinary = new File(TestBase.getProp().getProperty("firefoxpath"));
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		driver = new FirefoxDriver(ffBinary,profile);
		driver.manage().window().maximize();
	}
	public WebDriver getDriver() {
		return driver;
	}
}
