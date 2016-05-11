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
        caps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Linux; U; Android 2.3.3; en-us; LG-LU3000 Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

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