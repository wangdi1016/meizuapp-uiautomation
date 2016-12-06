package pageshelper;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import pages.ResultPage;
import utils.AppiumUtil;
import utils.ExcelUtil;
import utils.TestData;

public class ResultPageHelper {

	public static Logger logger = Logger.getLogger(ResultPageHelper.class);
	
	// Wait until ResultPage element loading complete
	public static void waitResultPageLoad(int timeOut) {
		logger.info("Waiting for the loading of ResultPage elments...");
		AppiumUtil.waitForElementToLoad(timeOut, ResultPage.RP_SEARCH_TEXT);
		AppiumUtil.waitForElementToLoad(timeOut, ResultPage.RP_RESULT_TITLE);
		logger.info("The loading of ResultPage elements complete.");
	}
	
	// Save the first 8 results to excel
	public static void saveResult(TestData testData, String resultPath) throws IOException {
		AppiumUtil.assertElementsExist(ResultPage.RP_RESULT_TITLE);
		List<WebElement> elements = AppiumUtil.elements(ResultPage.RP_RESULT_TITLE);
		for (WebElement element : elements) {
			System.out.println("Element title: " + element.getText());
		}
		ExcelUtil.writeResultToExcelFile(resultPath, testData, elements);
	}
	
	// Save screenshot of ResultPage
	public static void saveScreenshot(String screenshotPath) throws IOException {
		logger.info("Saving screenshot...");
		AppiumUtil.saveScreenshot(screenshotPath);
		logger.info("Screenshot saved.");
	}
	
	// Verify checkpoints of ResultPage
	public static void checkResult(TestData testData) {
		logger.info("Start verifying checkpoints of ResultPage...");
		// Verify the query in search text
		AppiumUtil.assertElementText(ResultPage.RP_SEARCH_TEXT, testData.getQuery());
		
		// Verify there is one result entry at least
		AppiumUtil.assertElementsExist(ResultPage.RP_CARDVIEW);
		AppiumUtil.assertNumberOfElements(ResultPage.RP_CARDVIEW, ">=", 1);
		
		// Verify the header elements in each card of the first page
		int numberOfCardview = AppiumUtil.numberOfElements(ResultPage.RP_CARDVIEW);
		AppiumUtil.assertElementsExist(ResultPage.RP_FUNCTION_APP_ICON);
		AppiumUtil.assertNumberOfElements(ResultPage.RP_FUNCTION_APP_ICON, "==", numberOfCardview);
		AppiumUtil.assertElementsExist(ResultPage.RP_FUNCTION_APP_NAME);
		AppiumUtil.assertNumberOfElements(ResultPage.RP_FUNCTION_APP_NAME, "==", numberOfCardview);
		AppiumUtil.assertElementsExist(ResultPage.RP_FUNCTION_NAME);
		AppiumUtil.assertNumberOfElements(ResultPage.RP_FUNCTION_NAME, "==", numberOfCardview);
		
		// Verify the vertical in first card is expected
		String expectedFirstCard = testData.getExpectedFirstCard();
		AppiumUtil.assertElementText(ResultPage.RP_FUNCTION_APP_NAME, 0, expectedFirstCard);
		
		logger.info("The checkpoints verification complete.");
	}
	
	// Switch to HomePage
	public static void switchToHomePage() {
		logger.info("Waiting for switching to HomePage...");
		AppiumUtil.click(ResultPage.RP_TOOLBAR_NAV_BUTTON);;
		logger.info("Have switched to HomePage.");
	}
	
	// Switch to SearchPage
	public static void switchToSearchPage() {
		logger.info("Waiting for switching to SearchPage...");
		AppiumUtil.click(ResultPage.RP_SEARCH_BAR_RIGHT_ICON);;
		logger.info("Have switched to SearchPage.");
	}
	
}
