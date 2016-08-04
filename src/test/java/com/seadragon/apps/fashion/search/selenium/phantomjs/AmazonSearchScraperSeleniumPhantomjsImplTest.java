package com.seadragon.apps.fashion.search.selenium.phantomjs;

import com.seadragon.apps.fashion.search.selenium.chrome.AmazonSearchScraperSeleniumChromeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.*;

/**
 * Created by hwang001c on 5/10/16.
 */
public class AmazonSearchScraperSeleniumPhantomjsImplTest {
    private WebDriver driver;
    private AmazonSearchScraperSeleniumPhantomjsImpl amazonSearch;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");

        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/home/hwang001c/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        driver = new PhantomJSDriver(caps);
        amazonSearch = new AmazonSearchScraperSeleniumPhantomjsImpl();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void search() throws Exception {
        amazonSearch.search(driver, "http://www.amazon.com");
    }

}