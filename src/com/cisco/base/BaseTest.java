package com.cisco.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import org.testng.annotations.Parameters;

import com.cisco.commonfunctions.uiapplibrary.LoginPage;
import com.cisco.util.ListenerToChangeTestName;
import com.cisco.util.TestUtil;
import com.cisco.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest extends ListenerToChangeTestName {

	public static String tenantName;

	public static Logger log = Logger.getLogger(BaseTest.class.getName());

	public static Xls_Reader dashboardxls = new Xls_Reader(
			System.getProperty("user.dir") + "//common//testdata//xls//dashboard2.xlsx");

	public static Xls_Reader datasetxls = new Xls_Reader(
			System.getProperty("user.dir") + "//common//testdata//xls//datasets.xlsx");

	public static ExtentReports report;

	public static ExtentTest test;
	public ITestResult result;
	public static boolean isLoggedIn = false;
	public static boolean isadminloggedIn = false;
	public static boolean isoperatorloggedIn = false;
	public static boolean isKMloggedIn = false;

	public static WebDriver driver = null;
	public static Properties OR = null;
	public static String timezone = null;

	public static boolean repCheck = false;
	public static Properties LCF = null;
	public static HttpURLConnection con = null;
	static TestUtil testObj = null;
	public static String accessValue = null;
	public static String LocationTypeId = null;

	public static PrintWriter pw;
	public static StringBuilder sbCsv = new StringBuilder();
	public static URL myurl;

	public static HashMap<String, String> configfile;

	public static String runon;
	public static String role;

	public static ArrayList<String> domainList = new ArrayList<String>();

	public static ArrayList<String> reportTypeList = new ArrayList<String>();
	public static File sensorCsvFile;

	private static WebDriverWait wait = null;
	private static WebElement element;

	public static Map<String,String> createdTemplateNames = new HashMap<String, String>();
	public static Map<String,String> createdConnectorNames = new HashMap<String, String>();
	
	public BaseTest() {

		LCF = new Properties();
		OR = new Properties();
		report = new ExtentReports(System.getProperty("user.dir") + "/test-output/Report/report.html", true);
		test = report.startTest("Start test");
		try {
			// config
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "//common//config//OR.properties");
			FileInputStream cf = new FileInputStream(
					System.getProperty("user.dir") + "//tenants//" + tenantName + "//config//Config.properties");
			LCF.load(cf);
			OR.load(fs);

		} catch (Exception e) {
			// error
			e.printStackTrace();

		}
	}

	public void deleteCookies() {
		try {
			driver.manage().deleteAllCookies();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String setConfig(String key) {

		// System.out.println(key +":"+ LCF.getProperty(key) );
		return LCF.getProperty(key);
	}

	public static void getbrowser() {
		if (setConfig("MultiBrowser").equalsIgnoreCase("false")) {

			System.out.println("Executing the tests in : " + setConfig("browser") + "Browser");

			if (setConfig("browser").equals("Mozilla")) {
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				capability.setAcceptInsecureCerts(true);
				FirefoxOptions foptions = new FirefoxOptions();
				foptions.setBinary("");
				foptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
				foptions.setAcceptInsecureCerts(true);
				FirefoxProfile profile = new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(true);
				foptions.setProfile(profile);
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "//geckodriver//geckodriver.exe");
				driver = new FirefoxDriver();
				test.log(LogStatus.INFO, "firefox Browser Opening");

			} else if (setConfig("browser").equals("IE")) {

				System.setProperty(InternetExplorerDriver.IGNORE_ZOOM_SETTING, "100");
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\IEDriver\\IEDriverServer.exe");

				driver = new InternetExplorerDriver();
//          zoom level -100%; protected mode -> truned off; security setting level to least and allow active contest.
//			driver.get("javascript:document.getElementById('overridelink').click();");

			} else if (setConfig("browser").equals("Chrome")) {

				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
				chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
				chromeOptions.addArguments("--disable-notifications");
				chromeOptions.addArguments("disable-infobars");
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.setAcceptInsecureCerts(true);
				chromeOptions.setUnhandledPromptBehaviour(null);

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\chromedriver\\chromedriver.exe");
				// dr.set(this.driver);
				driver = new ChromeDriver();
				driver.manage().deleteAllCookies();
				driver.navigate().refresh();
			}

		}
	}

	@Parameters({ "app" })
	@BeforeSuite()
	public static void ConfigDetails(String app) throws IOException, TimeoutException {

		getbrowser();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		LoginPage loginPage = new LoginPage();

		timezone = setConfig("timeZone");

		try {
			if (app.equals("admin")) {
				loginPage.doLoginAsAdmin();
				isadminloggedIn = true;
			} else if (app.equals("operator")) {
				loginPage.doLoginAsOperator();
				isoperatorloggedIn = true;
			} else if (app.equals("keymanagment")) {
				// loginPage.doLoginAsKeyManagement();
				isKMloggedIn = true;
			}

		} catch (Exception e) {
			System.out.println("Exception occured in dologgin method");
			Reporter.log("Exception occured in dologin method");
		}

	}

	public static void startReportType(String reportType) {

		// System.out.println("ReportTestName"+reportType); // it prints "Check name
		// test"

		if (reportTypeList.size() > 0) {

			if (!reportTypeList.contains(reportType)) {

				throw new SkipException("Reports are not supported for this provider ... skipping the tests");
			}
		}
	}

	public static void sendMail(String txtMessage) throws Exception {

		System.out.println("mail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", setConfig("smtp_host")); // SMTP Host
		props.put("mail.smtp.port", setConfig("smtp_port")); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(setConfig("username"), setConfig("password"));
			}
		};
		Session session = Session.getInstance(props, auth);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(setConfig("username")));
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(setConfig("cc")));// "pbundego@cisco.com"));//pikhare@cisco.com,prasprak@cisco.com,sbhaktha@cisco.com"));
			message.setSubject("API Automation Testsuite on tenant - " + tenantName + "  " + txtMessage);

			FileDataSource fds = new FileDataSource(System.getProperty("user.dir") + "//" + tenantName
					+ "//Test_emailable_report//Automation_Test_Report.html");
			message.setDataHandler(new DataHandler(fds));
			message.setFileName(fds.getName());
			// message.setFileName(System.getProperty("user.dir")+"//"+tenantName+"//Test_emailable_report//Automation_Test_Report.html");
			Transport.send(message);

			System.out.println("Done");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void createWriterCsv() throws IOException {
		java.util.Date now = new Date();
		// new File(outdir).mkdirs();
		sensorCsvFile = new File(System.getProperty("user.dir") + "//tenants//" + tenantName + "//csvReport.csv");

		// pw = new PrintWriter(new File("csvReport"+now.getTime()+".csv"));
		pw = new PrintWriter(sensorCsvFile);

	}

	public static File xmlFile(String domain) {
		return new File(System.getProperty("user.dir") + "//responseData//" + domain + ".xml");

	}

	/* Below methods are UI related ***************/

	public static FileReader getJsonFile(String domain) {
		try {
			return new FileReader(System.getProperty("user.dir") + "//responseData//" + domain + ".json");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return null;

	}

//This function is to get locator from object properties file
	public static String getLocator(String locatorKey) {
		String locatorValue = OR.getProperty(locatorKey);
		if (locatorValue == null) {
			return locatorKey;
		}
		return locatorValue;

	}

//this function is to do action on mouse hover
	public static WebElement mouseHover(String locatorkey) {

		WebElement element = getLocatorType(locatorkey);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		return element;

	}

	public static WebElement selectOptionByVisibleText(String locatorkey, String name) {

		WebElement element = getLocatorType(locatorkey);
		Select oSelect = new Select(element);
		oSelect.selectByVisibleText(name);
		return element;

	}

//this function is to do action double click
	public static WebElement doubleClick(String locatorkey) {
		log.info("clicking on " + locatorkey);
		WebElement element = getLocatorType(locatorkey);
		Actions action = new Actions(driver);
		action.doubleClick(element).build().perform();
		return element;

	}

//This function is to findelement based on different locator
	public static WebElement findElement(String elementvalue) {
		// String elementvalue = getLocator(locatorkey);

		if (elementvalue.startsWith("//")) {

			return driver.findElement(By.xpath(elementvalue));

		} else if (elementvalue.contains("[")) {
			return driver.findElement(By.cssSelector(elementvalue));

		} else {
			return driver.findElement(By.id(elementvalue));

		}

	}

	public static List<WebElement> findElements(String locatorkey) {
		String elementvalue = getLocator(locatorkey);

		if (elementvalue.startsWith("//")) {

			return driver.findElements(By.xpath(elementvalue));

		} else if (elementvalue.contains("[")) {
			return driver.findElements(By.cssSelector(elementvalue));

		} else {
			return driver.findElements(By.id(elementvalue));

		}

	}

//This function is 
	public static List<WebElement> getLocatorTypeList(String locatorkey) {
		String elementvalue = getLocator(locatorkey);

		if (isElementPresnet(locatorkey)) {
			return findElements(elementvalue);
		} else {

			Reporter.log("Elements not present: " + elementvalue);
			return null;
		}

	}

	public static WebElement getLocatorType(String locatorkey) {
		String elementvalue = getLocator(locatorkey);

		if (isElementPresnet(locatorkey)) {
			return findElement(elementvalue);
		} else {

			Reporter.log("Elements not present: " + elementvalue);
			return null;
		}

	}

	public static boolean isElementPresnet(String locatorkey) {

		String elementvalue = getLocator(locatorkey);
		// driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(findElement(elementvalue)));// visibilityOf.elementToBeClickable();
		try {

			findElement(elementvalue).isDisplayed();
			return true;

		} catch (NoSuchElementException e) {
			Reporter.log("Element not present: " + elementvalue);
			System.out.println("Element not present" + elementvalue);
			takeScreenshot("objectnotfound.jpg");
			return false;
		}

	}

	public static boolean checkElementPresent(String locatorkey) {
		String elementvalue = getLocator(locatorkey);

		// if(elementvalue.startsWith("//")){

		if (driver.findElements(By.xpath(elementvalue)).size() > 0) {
			return true;
		} else
			return false;

	}

	public static void highlighter(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
		driverwait(2000);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px grey');", element);

	}

	public static void click(String locatorkey) {
		log.info("clicking on " + locatorkey);
		highlighter(getLocatorType(locatorkey));
//		isElementPresnet(locatorkey);
		getLocatorType(locatorkey).click();
		test.log(LogStatus.INFO, "Clicking on " + locatorkey);
	}

// enter text to input field
	public static void input(String locatorkey, String text) {
		try {
			log.info("Entering input values for " + locatorkey);
			highlighter(getLocatorType(locatorkey));
//			isElementPresnet(locatorkey);
			getLocatorType(locatorkey).clear();
			driverwait(1000);
			getLocatorType(locatorkey).sendKeys(text);
			driverwait(1000);
			// driver.findElement(By.xpath("//*[@class='mat-option-text' and text()='
			// "+text+" ']")).click();
			test.log(LogStatus.INFO, "Entering input values for " + locatorkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceString(String propertyKey, String replaceString, String sourceString) {
		return getLocator(propertyKey).replace(replaceString, sourceString);
	}

	public static void enterKey(String locatorkey) throws InterruptedException {

		getLocatorType(locatorkey).sendKeys(Keys.ARROW_DOWN);

		getLocatorType(locatorkey).sendKeys(Keys.ENTER);
	}

	public static void downArrow(String locatorkey) throws InterruptedException {

		getLocatorType(locatorkey).sendKeys(Keys.ARROW_DOWN);

	}

	public static String getText(String locatorkey) throws Exception {
		log.info("get text value " + locatorkey);
		test.log(LogStatus.INFO, "get the text value" + locatorkey);
		return driver.findElement(By.xpath(getLocator(locatorkey))).getText();
	}

	public static String getClassAttribute(String locatorkey) throws Exception {
		log.info("get text value " + locatorkey);
		test.log(LogStatus.INFO, "get the text value" + locatorkey);
		return getLocatorType(locatorkey).getAttribute("class");
	}

	public static boolean isSelected(String locatorkey) throws Exception {
		log.info("get text value " + locatorkey);
		test.log(LogStatus.INFO, "get the text value" + locatorkey);
		return getLocatorType(locatorkey).isSelected();
	}

	public static int getChildrenCount(String locatorkey) {
		log.info("get text value " + locatorkey);
		List<WebElement> count = driver.findElements(By.xpath(getLocator(locatorkey)));
		return count.size();

	}

	public boolean clickEventsDeleteIcon() {
		click("EVENTS_DELETE_ICON");
		return true;

	}

	public static void takeScreenshot(String fileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		String destFile = dateFormat.format(new Date()) + "_" + fileName; // + ".png";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

// select item from option list
	public boolean selectitem(String xpathKey, String tagName, String itemtoselect) {

		boolean found = false;
		try {
			switch (tagName.toLowerCase()) {

			case "li":
				// this code to be cross checked
				// WebElement dropdown1 =
				// driver.findElement(By.xpath(OR.getProperty(xpathKey)));
				WebElement dropdown1 = getLocatorType(xpathKey);
				List<WebElement> list = dropdown1.findElements(By.tagName("li"));
				for (WebElement item : list) {
					if (item.getText().trim().equalsIgnoreCase(itemtoselect)) {
						item.click();
						found = true;
						log.info("Item selected from dropdown" + xpathKey);
						test.log(LogStatus.INFO, "Item selected from dropdown" + xpathKey);
						break;
					}

				}

				break;

			case "option":
				WebElement dropdown2 = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
				Select se = new Select(dropdown2);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

				int itemCount = se.getOptions().size();
				for (int i = 0; i < itemCount; i++) {

					if (se.getOptions().get(i).getText().equalsIgnoreCase(itemtoselect)) {
						se.selectByIndex(i);
						// se.selectByValue(itemtoselect);
						// se.selectByVisibleText(itemtoselect);
						found = true;
						log.info("Item selected from dropdown" + xpathKey);
						test.log(LogStatus.INFO, "Item selected from dropdown" + xpathKey);
						break;
					}
				}

				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

				break;

			case "div":

				break;

			}
		} catch (Exception e) {

			Reporter.log("Item could not be selected in dropdown: " + xpathKey);
			System.out.println("Item could not be selected in dropdown: " + xpathKey);
			takeScreenshot("itemnotfound.jpg");
			e.printStackTrace();
			found = false;
			return found;
		}

		if (!found) {
			System.out.println("Item to be selected is not found: " + itemtoselect);
			Reporter.log("Item to be selected is not found: " + itemtoselect);
		}
		return found;
	}

//dynamic data 
	public static String generateDynamicData() {
		Date today = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("ddmmss");
		String date = dateformat.format(today);
		return date;
		// System.out.println("Today in dd-MM-yyyy format : " + date);

	}

	public static void scroll_Down_And_Click(String locatorkey) {// ,String elementValue ){

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String eleValue = getLocator(locatorkey);
		WebElement element = findElement(eleValue);
		// element = findElement(elementValue);

		jse.executeScript("arguments[0].scrollIntoView();", element);
		click(locatorkey);

	}

	public static void scroll_Down(String locatorkey) {// ,String elementValue ){

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String eleValue = getLocator(locatorkey);
		WebElement element = findElement(eleValue);
		// element = findElement(elementValue);
		jse.executeScript("arguments[0].scrollIntoView();", element);
		// click(locatorkey);

	}

//scroll BaseTest
	public void scroll_Page_Down(int xpixels, int ypixles) throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(xpixels,ypixles)", "");
	}

//getting x and y points for the given object locator.
	public void getlocation(String locator) {
		Point point = findElement(locator).getLocation();

		System.out.println("X Position : " + point.getX());
		System.out.println("Y Position : " + point.getY());

	}

	public boolean selectItemInDivList(String xpathkey, String itemtoselect) {

		// WebDriverWait wait = new WebDriverWait(driver,600);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathkey)));

		try {
			WebElement divlistContainer = getLocatorType(xpathkey);
			// WebElement divlistContainer = driver.findElement(By.xpath(xpathkey));
			List<WebElement> divlistitems = divlistContainer
					.findElements(By.xpath("//div[starts-with(@id,'app_mainView_policesView_policyTypes_checkbox')]"));
			System.out.println(divlistitems.size());
			for (WebElement divitem : divlistitems) {

				String item = divitem.getText();
				System.out.println(item);
				if (item.contains(itemtoselect)) {
					// wait.until(ExpectedConditions.visibilityOfAllElements(divlistitems));
					divitem.click();
					break;
				}

			}

		} catch (Exception e) {
			Reporter.log("Item could not be selected in div list : " + xpathkey);
			System.out.println("Item could not be selected in div list: " + xpathkey);
			takeScreenshot("objectnotfound.jpg");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean selectitemByIndex(String xpathKey, String tagName, int index) {

		boolean found = false;

		// WebDriverWait wait = new WebDriverWait(driver,600);
		// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(xpathKey))));

		try {
			WebElement dropdown = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
			Select se = new Select(dropdown);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			int size = se.getOptions().size();
			if (size > 1) {
				se.selectByIndex(1);
				found = true;
			} else {
				System.out.println("No Event to be selected/applied in dropdown");
				found = false;
			}

			// driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		} catch (Exception e) {

			Reporter.log("Item could not be selected in dropdown: " + xpathKey);
			System.out.println("Item could not be selected in dropdown: " + xpathKey);
			takeScreenshot("itemnotfound.jpg");
			e.printStackTrace();
			return false;
		}

		return found;
	}

//custom implicit-wait
	public void implicitWait(int timeInSec) {
		// Reporter.log("waiting for BaseTest or element to load");
		try {
			driver.manage().timeouts().implicitlyWait(timeInSec, TimeUnit.SECONDS);
			// Reporter.log("BaseTest is loaded");
		} catch (Exception e) {
			System.out.println("Timeout for BaseTest load request to complete after " + timeInSec);
			Assert.assertTrue(false, "Timeout for BaseTest load request after " + timeInSec + "seconds");
		}
	}

//webdriver wait or explicitwait
	public WebElement waitForElement(WebDriver driver, long time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

//webdriver wait with polling interval
	public WebElement waitForElementwithPollingInterval(WebDriver driver, long time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public static void driverwait(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseMovement(String locatorKey) {
		WebElement element = getLocatorType(locatorKey);
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
		log.info("move mouse to element" + locatorKey);
	}
//	public static void leftArrow() throws InterruptedException {
//		Actions act = new Actions(driver);
////		act.sendKeys(Keys.ALT, Keys.ARROW_LEFT).perform();
//		act.keyDown(Keys.ALT).sendKeys(Keys.ARROW_LEFT).build().perform();
//
//	}

//common function for policy & Event
//click on deleteIcon

	public boolean clickDeleteIcon() {
		click("DELETE_ICON");
		return true;

	}
//click on Delete Button

	public boolean clickDeleteButton() {
		click("POLICY_EVENT_DELETE_BTN");
		return true;
	}

	public boolean clickElementById(String eid) {

		WebDriverWait wait = new WebDriverWait(driver, 6000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(eid)));

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		try {

			driver.findElement(By.id(eid)).click();
		} catch (Exception e) {
			System.out.println("Could not click element: " + eid);
			Reporter.log("Could not click element by id :" + eid);
			takeScreenshot("objectnotfound.jpg");
			e.printStackTrace();
			return false;

		}

		return true;
	}

	public boolean selectMenuToolBar(String locatorkey, String tabName) {
		click(replaceString(locatorkey, "REPLACE_STRING", tabName));
		return true;
	}

	public void refreshbrowser() {
		driver.navigate().refresh();

	}

	/* dateapi will be null for checking the lastupdated (feeds validation) */

	public static long getTimeDifference(String lastupdatedtime, String dateapi, String value) throws Exception {
		System.out.println("Last Updated time  " + lastupdatedtime + " for parameter " + value);
		Reporter.log("Last Updated time  " + lastupdatedtime + " for parameter " + value);

		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a MMM dd,yyyy");
		if (!dateapi.equals("")) {
			long l = Long.parseLong(dateapi);
			date = new Date(l);

			System.out.println("apidate before: " + df.format(date));
		} else {
			date = new Date();
		}
		df.setTimeZone(TimeZone.getTimeZone(setConfig("timeZone")));
		System.out.println("apidate after timezone: " + df.format(date));
		SimpleDateFormat lastdf = new SimpleDateFormat("hh:mm:ss yyyy-MM-DD");
		// Date ldate =lastdf.parse(lastupdatedtime);
		// System.out.println("last upadted from dashborad: " + df.format(ldate) );
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(lastupdatedtime);
			// d1 = df.parse(df.format(ldate));
			d2 = df.parse(df.format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		System.out.println(diff);
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");
		System.out.println("Time in hours: " + diffHours + " hours.");

		/*
		 * Reporter.log("Time difference in Seconds is " +diffSeconds);
		 * Reporter.log("Time difference is minutes is " +diffMinutes);
		 * Reporter.log("Time difference in hours is " +diffHours);
		 */

		return diffHours;

	}

//this static function is responsible for html report
	static {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		report = new ExtentReports(System.getProperty("user.dir") + "//tenants//automation//report"
				+ formater.format(calender.getTime()) + ".html", false);
	}

	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + "test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + "test is skipped and skip reason is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getName() + "test is failed" + result.getThrowable());
//		 takeScreenshot(result.getName());
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + "test is started");
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		getresult(result);
	}

	@BeforeMethod
	public void beforemethod(ITestResult result) {
		test = report.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + "test started");
	}

//@AfterClass(alwaysRun=true)
	public void endTest() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

	public static String getTextColor(String locatorkey) throws Exception {
		log.info("get text value " + locatorkey);
		test.log(LogStatus.INFO, "get the text value" + locatorkey);
		return driver.findElement(By.xpath(getLocator(locatorkey))).getCssValue("color");
	}

	public static String getCurrentDate() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		return dtf.format(now).toString();
	}

	public static void input1(String locatorkey, String text) {
		try {
			log.info("Entering input values for " + locatorkey);
			getLocatorType(locatorkey).sendKeys(text);
			driverwait(1000);
			// driver.findElement(By.xpath("//*[@class='mat-option-text' and text()='
			// "+text+" ']")).click();
			test.log(LogStatus.INFO, "Entering input values for " + locatorkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ********

	public void verifyToastNotifications(String ToastNotification) {
		try {
			if (getText("Toast_containter").contentEquals(ToastNotification)) {
				log.info("Toast Notification - " + ToastNotification);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickAndHold(String locatorKey, String locatorKey1) {
		WebElement element = getLocatorType(locatorKey);
		WebElement element1 = getLocatorType(locatorKey1);
		Actions act = new Actions(driver);
		act.clickAndHold(element).pause(2000).moveToElement(element1).release().build().perform();
		log.info("move mouse to element" + locatorKey);
	}

}
