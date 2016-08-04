package com.seadragon.apps.fashion.search.selenium.chrome;

import static com.seadragon.apps.fashion.util.FashionUtil.saveScreenShot;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hwang001c on 5/7/16.
 */
public class AmazonSearchScraperSeleniumChromeImpl {
    private static final Logger logger = LoggerFactory.getLogger(AmazonSearchScraperSeleniumChromeImpl.class);


    public void search(WebDriver driver, String url) {
//        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
//        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//        driver.navigate().to(url);
//        WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
//        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Go']"));
//        saveScreenShot(driver);
//        logger.info(searchTextBox.getTagName());
//        searchTextBox.sendKeys("shoes for boys");
//        submitButton.click();
////        driver.findElement(By.cssSelector("a[href*='Boys']")).click();
//        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li[contains(@class, 's-result-item')]/div[@class='s-item-container']/div/div/div/span/div/a[contains(@class, 'a-link-normal')]"));
//        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
//
//        String base = driver.getWindowHandle();
//        for (int i = 0; i < linkList.size(); i++) {
//            Actions action = new Actions(driver);
//            linkList.get(i).sendKeys(selectLinkOpeninNewTab);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                logger.error(ex.getLocalizedMessage(), ex);
//            }
//            action.keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.TAB).keyUp(Keys.LEFT_CONTROL).perform();
//            Set<String> tabs = driver.getWindowHandles();
//            while (tabs.size() < 2) {
//                try {
//                    logger.info("tabs: " + tabs.size());
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    logger.error(ex.getLocalizedMessage(), ex);
//                }
//                tabs = driver.getWindowHandles();
//            }
//            String newTab = null;
//            for (String tab : tabs) {
//                logger.info("------" + tab + "-------");
//                if (!tab.equals(base)) {
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
//            } while (!newTabReady);
//
//            List<WebElement> sizeList = driver.findElements(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownAvailable']"));
//            for (int j = 0; j < sizeList.size(); j++) {
//                sizeList.get(j).click();
//                List<WebElement> colorList = driver.findElements(By.xpath("//div[@id='variation_color_name']/ul/li[@class='swatchAvailable']/span/div/span/span/span/button[@class='a-button-text']"));
//                for (int k = 0; k < colorList.size(); k++) {
//                    colorList.get(k).click();
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException ex) {
//                        logger.error(ex.getLocalizedMessage(), ex);
//                    }
//
//                    WebElement productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
//                    while (productColor == null) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException ex) {
//                            logger.error(ex.getLocalizedMessage(), ex);
//                        }
//                        productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
//                    }
//                    WebElement productTitle = driver.findElement(By.id("productTitle"));
//                    WebElement productPrice = null;
//                    try {
//                        productPrice = driver.findElement(By.id("priceblock_ourprice"));
//                    } catch (NoSuchElementException nsee) {
//                        try {
//                            productPrice = driver.findElement(By.id("priceblock_saleprice"));
//                        } catch (NoSuchElementException nsee2) {
//                            continue;
//                        }
//                    }
//                    String productSize = sizeList.get(j).getText();
//                    System.out.println(productTitle.getText() + "|" + productSize + "|" + productColor.getText() + "|" + (productPrice.getText()));
//                    logger.info(productTitle.getText() + "|" + productSize + "|" + productColor.getText() + "|" + (productPrice.getText()));
//                }
//            }
//            logger.info(driver.getTitle());
////            action.keyDown(Keys.LEFT_CONTROL).sendKeys("w").keyUp(Keys.LEFT_CONTROL).perform();
//            driver.close();
//            // driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.LEFT_CONTROL+"w"));
//            driver.switchTo().window(base);
//        }
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.get(url);
        WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Go']"));
        saveScreenShot(driver);
        logger.info(searchTextBox.getTagName());
        searchTextBox.sendKeys("shoes for boys");
        submitButton.click();
        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li[contains(@class, 's-result-item')]/div[@class='s-item-container']/div/div/div/span/div/a[contains(@class, 'a-link-normal')]"));
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < linkList.size(); i++) {
            urlList.add(linkList.get(i).getAttribute("href"));
        }
        for (String s : urlList) {
            driver.get(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }

            List<WebElement> sizeList = driver.findElements(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownAvailable']"));
            for (int j = 0; j < sizeList.size(); j++) {
                sizeList.get(j).click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error(ex.getLocalizedMessage(), ex);
                }
                List<WebElement> colorList = driver.findElements(By.xpath("//div[@id='variation_color_name']/ul/li[@class='swatchAvailable']/span/div/span/span/span/button[@class='a-button-text']"));
                for (int k = 0; k < colorList.size(); k++) {
                    colorList.get(k).click();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        logger.error(ex.getLocalizedMessage(), ex);
                    }

                    WebElement productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
                    while (productColor == null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            logger.error(ex.getLocalizedMessage(), ex);
                        }
                        productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
                    }
                    WebElement productTitle = driver.findElement(By.id("productTitle"));
                    WebElement productPrice = null;
                    try {
                        productPrice = driver.findElement(By.id("priceblock_ourprice"));
                    } catch (NoSuchElementException nsee) {
                        try {
                            productPrice = driver.findElement(By.id("priceblock_saleprice"));
                        } catch (NoSuchElementException nsee2) {
                            continue;
                        }
                    }
                    String productSize = sizeList.get(j).getText();
                    System.out.println(productTitle.getText() + "|" + productSize + "|" + productColor.getText() + "|" + (productPrice.getText()));
                    logger.info(productTitle.getText() + "|" + productSize + "|" + productColor.getText() + "|" + (productPrice.getText()));
                }
            }
        }
        driver.quit();
    }
}
