package com.seadragon.apps.fashion.search.selenium.firefox;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hwang001c on 5/7/16.
 */
public class AmazonSearchScraperSeleniumFirefoxImpl {
    private static final Logger logger = LoggerFactory.getLogger(AmazonSearchScraperSeleniumFirefoxImpl.class);


    public void search(WebDriver driver, String url) {
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.navigate().to(url);
        WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Go']"));
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("screenshot_.png"));
        }catch(IOException ex){
            logger.error(ex.getLocalizedMessage(), ex);
        }

        searchTextBox.sendKeys("shoes for boys");
        submitButton.click();
//        driver.findElement(By.cssSelector("a[href*='Boys']")).click();
        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li[contains(@class, 's-result-item')]/div[@class='s-item-container']/div/div/div/span/div/a[contains(@class, 'a-link-normal')]"));
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);

        String base = driver.getWindowHandle();
        for (int i = 0; i < linkList.size(); i++) {
            Actions action = new Actions(driver);
            linkList.get(i).sendKeys(selectLinkOpeninNewTab);
            try {
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                logger.error(ex.getLocalizedMessage(), ex);
            }
            action.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.TAB).keyUp(Keys.LEFT_CONTROL).perform();
//            Set<String> tabs = driver.getWindowHandles();
//            while(tabs.size() < 2){
//                try {
//                    Thread.sleep(1000);
//                }catch(InterruptedException ex){
//                    logger.error(ex.getLocalizedMessage(), ex);
//                }
//                tabs = driver.getWindowHandles();
//            }
//            String newTab = null;
//            for(String tab: tabs){
//                logger.info("------"+tab+"-------");
//                if(!tab.equals(base)){
//                    newTab = tab;
//                    break;
//                }
//            }
//
//            boolean newTabReady = false;
//            do {
//                try {
//                    logger.info("new tab is not ready!");
//                    driver.switchTo().window(newTab);
//                    newTabReady = true;
//                } catch (NullPointerException ex) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException iex) {
//                        logger.error(ex.getLocalizedMessage(), iex);
//                    }
//                }
//            }while(!newTabReady);
            driver.switchTo().defaultContent();

            List<WebElement> sizeList = driver.findElements(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownAvailable']"));
            for (int j = 0; j < sizeList.size(); j++) {
                sizeList.get(j).click();
                List<WebElement> colorList = driver.findElements(By.xpath("//div[@id='variation_color_name']/ul/li[@class='swatchAvailable']/span/div/span/span/span/button[@class='a-button-text']"));
                for (int k = 0; k < colorList.size(); k++) {
                    colorList.get(k).click();
                    WebElement productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
                    while(productColor == null){
                        try {
                            Thread.sleep(2000);
                        }catch(InterruptedException ex){
                            logger.error(ex.getLocalizedMessage(), ex);
                        }
                        productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
                    }
                    WebElement productTitle = driver.findElement(By.id("productTitle"));
//                    logger.info(driver.getPageSource());

                    WebElement productPrice = null;
                    try {
                        productPrice = driver.findElement(By.xpath("//span[@id='priceblock_ourprice']"));
                    }catch(NoSuchElementException nsee){
                        continue;
                    }
                    String productSize = sizeList.get(j).getText();
                    logger.info(productTitle.getText());
                    logger.info(productSize);
                    logger.info(productColor.getText());
                    logger.info(productPrice.getText());
                }
            }
            logger.info(driver.getTitle());
            action.keyDown(Keys.LEFT_CONTROL).sendKeys("w").keyUp(Keys.LEFT_CONTROL).perform();
//            driver.close();
            // driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.LEFT_CONTROL+"w"));
            driver.switchTo().window(base);
        }
    }

}
