package com.seadragon.apps.fashion.scraper.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by hwang001c on 5/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/fashion-application-context-test.xml"})
public class AmazonItemScraperSeleniumImplTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "/home/hwang001c/local/chrome/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testChromeSelenium() {
        driver.get("http://www.amazon.com/");
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
    @Test
    public void scrape() throws Exception {

    }

}