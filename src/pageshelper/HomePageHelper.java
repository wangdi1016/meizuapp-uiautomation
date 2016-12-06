package pageshelper;

import pages.HomePage;
import utils.AppiumUtil;

import org.apache.log4j.Logger;

public class HomePageHelper {

	public static Logger logger = Logger.getLogger(HomePageHelper.class);
	
	// Wait until HomePage element loading complete
	public static void waitHomePageLoad(int timeOut) {
		logger.info("Waiting for the loading of HomePage elments...");
		AppiumUtil.waitForElementToLoad(timeOut, HomePage.HP_SEARCH);
		logger.info("The loading of HomePage elements complete.");
	}
	
	// Switch to SearchPage
	public static void switchToSearchPage() {
		logger.info("Waiting for switching to SearchPage...");
		AppiumUtil.click(HomePage.HP_SEARCH);;
		logger.info("Have switched to SearchPage.");
	}
}
