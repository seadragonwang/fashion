package com.seadragon.apps.fashion.search.selenium.chrome;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.*;

/**
 * Created by hwang001c on 5/9/16.
 */
public class AmazonSearchScraperSeleniumChromeImplTest {
    private WebDriver driver;
    private AmazonSearchScraperSeleniumChromeImpl amazonSearch;
    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/home/hwang001c/local/chrome/chromedriver");

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "/home/hwang001c/local/chrome/chromedriver");
        driver = new ChromeDriver(caps);
        amazonSearch = new AmazonSearchScraperSeleniumChromeImpl();
    }

    @Test
    public void testSearch() throws Exception {
        amazonSearch.search(driver, "http://www.amazon.com");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}