package com.seadragon.apps.fashion.search.selenium.firefox;

import com.seadragon.apps.fashion.search.selenium.chrome.AmazonSearchScraperSeleniumChromeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.*;

/**
 * Created by hwang001c on 5/10/16.
 */
public class AmazonSearchScraperSeleniumFirefoxImplTest {
    private WebDriver driver;
    private AmazonSearchScraperSeleniumFirefoxImpl amazonSearch;
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        amazonSearch = new AmazonSearchScraperSeleniumFirefoxImpl();
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