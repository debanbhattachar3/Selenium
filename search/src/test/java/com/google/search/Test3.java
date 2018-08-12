package com.google.search;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageClass.WebDriver_All;

public class Test3 {
	public WebDriver driver;
	public WebDriver_All browser = new WebDriver_All(driver);

	@Test
	public void test() {
		assertTrue(true);
	}

	@BeforeTest
	public void beforeTest() throws Exception {
		browser = browser.navigateBrowser("https://mvnrepository.com", "chrome");
		browser.windowMaximize();
		Thread.sleep(3000);
		Reporter.log("Browser Open", true);

	}

	@AfterTest
	public void afterTest() {
		browser.quit();
	}
}
