package com.testautomation.bookstore.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.bookstore.abstractComponents.AbstractComponents;
import com.testautomation.bookstore.pageObjects.HomePage;
import com.testautomation.bookstore.pageObjects.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public HomePage homePage;
	public AbstractComponents abstractComponents;

	public WebDriver initializeDriver() throws IOException {

		// Properties object creation
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//com//testautomation//bookstore//resources//GlobalData.properties");
		prop.load(fis);

		// give priority to maven to select which browser to choose at runtime using
		// java ternary operator ? and :
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();

			// running in headless mode
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}

			driver = new ChromeDriver(options);

			// maximizing in headless mode using custom settings
			driver.manage().window().setSize(new Dimension(1440, 900));

		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver",
					"D:\\Samina\\Drivers\\geckodriver-v0.36.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();

		return driver;
	}

	public List<Map<String, Object>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// convert string to map using Jackson
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<Map<String, Object>>>() {});

	}

	@BeforeMethod(alwaysRun = true)
	public void launchApplication() throws IOException {
		driver = initializeDriver();
		driver.get("https://demowebshop.tricentis.com/");
		abstractComponents = new AbstractComponents(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeMethod(alwaysRun = true, onlyForGroups = { "requires-login" })
	public void loginIfRequired() {
		LoginPage loginPage = new LoginPage(driver);
		homePage = loginPage.login("ABone@email.com", "A1Bone");
	}

	public static String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(path));
		return path;
	}
	
	public Object[][] getTestDataFromJson(String filePath) throws IOException {
	    List<Map<String, Object>> data = getJsonDataToMap(filePath);
	    Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}

	public Object[][] getStructuredTestData(String filePath, String inputKey, String outputKey) throws IOException {
	    List<Map<String, Object>> testData = getJsonDataToMap(filePath);
	    Object[][] data = new Object[testData.size()][2];

	    for (int i = 0; i < testData.size(); i++) {
	        data[i][0] = testData.get(i).get(inputKey);
	        data[i][1] = testData.get(i).get(outputKey);
	    }

	    return data;
	}
}
