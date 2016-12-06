package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class AppiumUtil {

	private static AndroidDriver driver;
	private static Logger logger;
	
	public static void init(AndroidDriver webDriver) {
		driver = webDriver;
		logger = Logger.getLogger(AppiumUtil.class.getName());
	}
	
	public static WebElement element(By by) {
		return driver.findElement(by);
	}
	
	public static List<WebElement> elements(By by) {
		return driver.findElements(by);
	}
	
	public static int numberOfElements(By by) {
		return ((List<WebElement>)elements(by)).size();
	}
	
	public static void assertNumberOfElements(By by, String comparator, int threshold) {
		int actualNumber = numberOfElements(by);
		switch (comparator) {
		case "==":
			Assert.assertTrue(actualNumber == threshold);
			break;
		case "<":
			Assert.assertTrue(actualNumber < threshold);
			break;
		case "<=":
			Assert.assertTrue(actualNumber <= threshold);
			break;
		case ">":
			Assert.assertTrue(actualNumber > threshold);
			break;
		case ">=":
			Assert.assertTrue(actualNumber >= threshold);
			break;
		}
	}
	
	public static void click(By by) {
		element(by).click();
		logger.debug(String.format("click by: %s successfully", by));
	}
	
	public static void input(By by, String text) {
		WebElement e = element(by);
		e.clear();
		e.sendKeys(text);
		logger.debug(String.format("click by: %s, and input %s successfully", by, text));
	}
	
	public static void pressKeyCode(int key) {
		driver.pressKeyCode(key);
	}
	
	public static void pressEnterKey() {
		pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	public static void assertElementText(By by, String expected) {
		Assert.assertEquals(element(by).getText(), expected);
	}
	
	public static void assertElementText(By by, int index, String expected) {
		Assert.assertEquals(elements(by).get(index).getText(), expected);
	}
	
	public static boolean doesElementExist(By by) {
		try {
			element(by);
			return true;
		} catch(NoSuchElementException nee) {
			return false;
		}
	}
	
	public static void assertElementExist(By by) {
		Assert.assertTrue(doesElementExist(by));
	}
	
	public static boolean doElementsExist(By by) {
		try {
			elements(by);
			return true;
		} catch(NoSuchElementException nee) {
			return false;
		}
	}
	
	public static void assertElementsExist(By by) {
		Assert.assertTrue(doElementsExist(by));
	}
	
	public static void waitForElementToLoad(int timeOut, By by) {
		logger.info("Start searching element[" + by + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(by);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			logger.error("Timeout! Did not find the element [" + by + "] after " + timeOut + "seconds.");
			Assert.fail("Timeout! Did not find the element [" + by + "] after " + timeOut + "seconds.");

		}
		logger.info("Found the element [" + by + "]");
	}
	
	public static void saveScreenshot(String screenshotPath) throws IOException {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		File targetFile = new File(screenshotPath);
		FileUtils.copyFile(srcFile, targetFile);
	}
	
	public static void waitForLoad() {
	    ExpectedCondition<Boolean> pageLoad= new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((AndroidDriver)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(driver, 30);
	    wait.until(pageLoad);
	}
	
}
