package pageClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriver_All {

	/*
	 * Log both in the file and console as per the Log4j configuration file log
	 * file location = "./logs/app.log"
	 */
	public static final Logger log = LogManager.getLogger(WebDriver_All.class.getName());
	public WebDriver driver;

	// Constructor for WebContoller
	public WebDriver_All(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Open Browser and navigate to the URL
	 * 
	 * @param baseURL
	 * @param browser
	 * @return WebController
	 */

	public WebDriver_All navigateBrowser(String baseURL, String browser) {

		System.gc(); // garbage collector
		log.info("\n");
		log.info("-------------------------------------------------------------");
		WebDriver driver = buildBrowser(browser);
		WebDriver_All browser_driver = new WebDriver_All(driver);
		log.info("Starting WebDriver:  Browser: {}  Version: {}  Platform: {}  ",
				browser_driver.getBrowserName().trim(), browser_driver.getBrowserVersion().trim(),
				browser_driver.Platform_Details().toString());
		log.info("Navigating to URL : {}", baseURL);
		log.info("-------------------------------------------------------------");
		driver.get(baseURL);
		return new WebDriver_All(driver);
	}

	/**
	 * Build the browser as per the browser name
	 * 
	 * @param browserName
	 * @return WebDriver
	 */

	public static WebDriver buildBrowser(String browserName) {
		String sysEnv = System.getenv("DRIVERS_PATH");
		if (sysEnv == null) {
			log.error("drivers path is NOT set in system environment variables.  "
					+ "Set DRIVERS_PATH = 'driver_location' and restart the PC");
			throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
		}
		String browserNewName = browserName.replaceAll(" ", "");
		if (browserNewName.equalsIgnoreCase("chrome") || browserNewName.equalsIgnoreCase("googlechrome")) {
			System.setProperty("webdriver.chrome.driver", sysEnv + "/chromedriver.exe");
			return new ChromeDriver();
		} else if (browserNewName.equalsIgnoreCase("ff") || browserNewName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", sysEnv + "/geckodriver.exe");
			return new FirefoxDriver();
		} else if (browserNewName.equalsIgnoreCase("ie") || browserNewName.equalsIgnoreCase("internetexplorer")) {
			System.setProperty("webdriver.ie.driver", sysEnv + "/IEDriverServer.exe");
			return new InternetExplorerDriver();
		} else if (browserNewName.equalsIgnoreCase("safari")) {
			return new SafariDriver();
		} else if (browserNewName.equalsIgnoreCase("edge") || (browserNewName.equalsIgnoreCase("microsoftedge"))) {
			return new EdgeDriver();
		} else {
			log.error("Browser not defined properlly, launching Chrome browser");
			System.setProperty("webdriver.chrome.driver", sysEnv + "/chromedriver.exe");
			return new ChromeDriver();
		}
	}

	/*
	 * This method is to get the Browser Version Details
	 */

	public String getBrowserVersion() {
		Capabilities capabilities = ((RemoteWebDriver) this.driver).getCapabilities();
		return capabilities.getVersion().toUpperCase();
	}

	/*
	 * This method is to get the Platform of the browser :: Details
	 */

	public String Platform_Details() {
		String version = System.getProperty("os.version");
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "Windows " + version;
		} else if (os.contains("nux") || os.contains("nix")) {
			return "Linux " + version;
		} else if (os.contains("mac")) {
			return "Mac " + version;
		} else {
			return "Other " + version;
		}
	}

	/*
	 * This method is to get the Browser Name :: Details
	 */

	public String getBrowserName() {
		Capabilities capabilities = ((RemoteWebDriver) this.driver).getCapabilities();
		return capabilities.getBrowserName().toUpperCase();
	}

	public void windowMaximize() {
		this.driver.manage().window().maximize();
		log.info("Browser Window is maximize");
	}

	public void quit() {
		if (driver != null) {
			log.info("Quit the WebDriver ");
			driver.quit();
		}
	}

	public void waitBySeconds(int timeunit_second) {
		try {
			TimeUnit.SECONDS.sleep(timeunit_second);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

	}

	/**
	 * Explicit Delay method | Delay by the time unit in second.
	 * 
	 * @param TIMEUNIT_Seconds
	 */

	public void DriverDelaybySeconds(int TIMEUNIT_Seconds) {
		try {
			TimeUnit.SECONDS.sleep(TIMEUNIT_Seconds);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Generic method to get the element by locator and type of the locator
	 * 
	 * valid types are : id , classname ; xpath , cssselector , partialLinktext
	 * 
	 * @param locator
	 * @param type
	 * @return WebElement
	 */

	public WebElement getElement(String locator, String type) {
		// convert type to lower case
		type = type.toLowerCase();
		// by id
		if (type.equals("id")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			return driver.findElement(By.id(locator));
		}
		// by xpath
		else if (type.equals("xpath")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			return driver.findElement(By.xpath(locator));
		}
		// by class name
		else if (type.equals("classname")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			return driver.findElement(By.className(locator));
		}
		// by css selector
		else if (type.equals("cssselector")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			return driver.findElement(By.cssSelector(locator));
		}
		// by partialLinktext
		else if (type.equals("partiallinktext")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			return driver.findElement(By.partialLinkText(locator));
		}
		// invalid type
		else {
			log.error("Locator and type is NOT supported. Please check the locator and type");
			return null;
		}
	}

	/**
	 * Generic method to get list of elements by locator and type of the locator
	 * 
	 * valid types are : id , classname ; xpath , cssselector , partialLinktext
	 * 
	 * @param locator
	 * @param type
	 * @return WebElement
	 */

	public List<WebElement> getAllElement(String locator, String type) {
		// convert type to lower case
		type = type.toLowerCase();
		List<WebElement> element_List = new ArrayList<WebElement>();
		// by id
		if (type.equals("id")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			element_List = driver.findElements(By.id(locator));
		}
		// by xpath
		else if (type.equals("xpath")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			element_List = driver.findElements(By.xpath(locator));
		}
		// by class name
		else if (type.equals("classname")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			element_List = driver.findElements(By.className(locator));
		}
		// by css selector
		else if (type.equals("cssselector")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			element_List = driver.findElements(By.cssSelector(locator));
		}
		// by partialLinktext
		else if (type.equals("partiallinktext")) {
			log.info("Finding element by " + type + " and locator is: " + locator);
			element_List = driver.findElements(By.partialLinkText(locator));
		}
		// invalid type
		else {
			log.error("Locator and type is NOT supported. Please check the locator and type");
			return null;
		}

		if (element_List.isEmpty()) {
			log.info("Element NOT found with the locator: " + locator + " and " + "type: " + type);
		} else {
			log.info("Element found with the locator: " + locator + " and " + "type: " + type);
		}

		return element_List;
	}

	/**
	 * 
	 * Verify whether element is present or not
	 * 
	 * @param locator
	 * @param type
	 * @return boolean value true and false
	 */

	public boolean isElementPresent(String locator, String type) {
		List<WebElement> all_elements = getAllElement(locator, type);

		int size = all_elements.size();
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will return the attribute value
	 * 
	 * @param AttibuteName
	 * @param locator
	 * @param type
	 * @return
	 */

	// add the logic for attribute name is invalid *******

	public String getAttrubuteValue(String AttibuteName, String locator, String type) {
		String returnValue = null;
		List<WebElement> elements = getAllElement(locator, type);
		if (elements.size() == 0) {
			log.info("Attribute type " + AttibuteName + " is NOT valid a type");
			return null;
		} else {
			log.info("Value for " + AttibuteName + " is : " + returnValue);
			return returnValue;
		}

	}

	/**
	 * Method to find out the inner text of the Web Element
	 * 
	 * @param element
	 *            of WebElement Type
	 * 
	 * @return
	 */

	public String getTextValue(WebElement element) {
		String returnValue;
		returnValue = element.getText();
		if (returnValue == null) {
			log.info("Element have no Text Value");
		}
		log.info("Text Value is : " + returnValue);
		return returnValue;
	}
}
