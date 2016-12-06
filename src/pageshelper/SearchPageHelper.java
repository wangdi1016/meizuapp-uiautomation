package pageshelper;

import org.apache.log4j.Logger;

import pages.SearchPage;
import utils.AppiumUtil;

public class SearchPageHelper {

	public static Logger logger = Logger.getLogger(SearchPageHelper.class);
	
	// Wait until SearchPage element loading complete
	public static void waitSearchPageLoad(int timeOut) {
		logger.info("Waiting for the loading of SearchPage elments...");
		AppiumUtil.waitForElementToLoad(timeOut, SearchPage.SP_SEARCH_TEXT);
		AppiumUtil.waitForElementToLoad(timeOut, SearchPage.SP_SEARCH_BUTTON);
		logger.info("The loading of SearchPage elements complete.");
	}
	
	// Search query
	public static void search(String query) throws InterruptedException {
		logger.info("Preparing to search...");
		AppiumUtil.input(SearchPage.SP_SEARCH_TEXT, query);
		Thread.sleep(5000);
		// AppiumUtil.pressEnterKey();
		AppiumUtil.click(SearchPage.SP_SEARCH_BUTTON);
		logger.info("Search complete.");
	}
	
}
