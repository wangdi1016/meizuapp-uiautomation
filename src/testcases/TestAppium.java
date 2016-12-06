package testcases;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import pageshelper.HomePageHelper;
import pageshelper.ResultPageHelper;
import pageshelper.SearchPageHelper;
import utils.AppiumUtil;
import utils.ExcelUtil;
import utils.TestData;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;

public class TestAppium {
	private AndroidDriver driver;
	private String dataPath;
	private String resultPath;
	private String appiumServerURL;
	
	@DataProvider(name="testData")
	public Iterator<Object[]> data() throws IOException {
		return new ExcelUtil(dataPath, resultPath);
	}
	
	@Test(dataProvider="testData")
	public void f(TestData testData) throws InterruptedException, IOException {
		// Enter HomePage
		HomePageHelper.waitHomePageLoad(10);
		
		// Switch to SearchPage
		HomePageHelper.switchToSearchPage();
		SearchPageHelper.waitSearchPageLoad(10);
		
		String query = testData.getQuery();
		
		// Search query and enter ResultPage
		SearchPageHelper.search(query);
		ResultPageHelper.waitResultPageLoad(10);
		
		// Save the first 8 results to excel
		ResultPageHelper.saveResult(testData, resultPath);
		
		// Return to HomePage
		ResultPageHelper.switchToHomePage();
	}
	
	@BeforeTest
	public void beforeTest() throws IOException {
		//dataPath = "/Users/diwang/di/work/test/meizu_ads/UI/test_data.xlsx";
		//resultPath = "/Users/diwang/di/work/test/meizu_ads/UI/result.xlsx";
		dataPath = System.getProperty("dataPath");
		resultPath = System.getProperty("resultPath");
		appiumServerURL = "http://localhost:4723/wd/hub";
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","TONY ISL0265");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("appPackage", "com.meizu.mstore");
        capabilities.setCapability("appActivity", "com.meizu.flyme.appcenter.activitys.AppMainActivity");
        capabilities.setCapability("newCommandTimeout", "7200");
        capabilities.setCapability("unicodeKeyboard", "True");
        capabilities.setCapability("resetKeyboard", "True");
        driver = new AndroidDriver<>(new URL(appiumServerURL), capabilities);
        AppiumUtil.init(driver);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
