package com.google.search;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageClass.WebDriver_All;

public class Test1 {
	public WebDriver driver;
	public WebDriver_All browser = new WebDriver_All(driver);
	public static final Logger log = LogManager.getLogger(Test1.class.getName());

	@Test
	public void test() {
		assertTrue(true);
	}
/*
 * Change
 */
	@BeforeTest
	public void beforeTest() throws Exception {
		browser = browser.navigateBrowser("https://lifescience.roche.com/en_in.html", "chrome");
		browser.windowMaximize();
		Thread.sleep(3000);
		Reporter.log("Browser Open", true);

	}

	@AfterTest
	public void afterTest(){
		browser.quit();
	}

}
