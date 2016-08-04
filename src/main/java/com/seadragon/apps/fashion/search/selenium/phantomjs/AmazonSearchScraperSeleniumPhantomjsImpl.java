package com.seadragon.apps.fashion.search.selenium.phantomjs;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static com.seadragon.apps.fashion.util.FashionUtil.*;

/**
 * Created by hwang001c on 5/7/16.
 */
public class AmazonSearchScraperSeleniumPhantomjsImpl {
    private static final Logger logger = LoggerFactory.getLogger(AmazonSearchScraperSeleniumPhantomjsImpl.class);


    public void search(WebDriver driver, String url) {
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.get(url);
        saveSnapshot(driver);
        logger.info(driver.getPageSource());
        WebElement searchTextBox = null;
        WebElement submitButton = null;
        try {
            searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
            submitButton = driver.findElement(By.xpath("//input[@value='Go']"));
        }catch(NoSuchElementException ex){
            searchTextBox = driver.findElement(By.id("nav-search-keywords"));
            submitButton = driver.findElement(By.xpath("//form[@id='nav-search-form']/div/div/input[@type='submit']"));
        }

        searchTextBox.sendKeys("shoes for boys");
        submitButton.click();
        waitForLoad(driver);
//        driver.findElement(By.cssSelector("a[href*='Boys']")).click();
        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li[contains(@class, 's-result-item')]/div[@class='s-item-container']/div/div/span/div/a[contains(@class, 'a-link-normal')]"));
        if(linkList == null || linkList.size() == 0){
            linkList = driver.findElements(By.xpath("//ul[@id='resultItems']/li[@class='sx-table-item']/a[contains(@class, 'a-link-normal')]"));
        }

        List<String> urlList = new ArrayList<>();
        for(WebElement we : linkList){
            urlList.add(we.getAttribute("href"));
        }

        for (int i = 0; i < urlList.size(); i++) {
            driver.get(urlList.get(i));
//                logger.info(driver.getPageSource());
            List<WebElement> sizeList = driver.findElements(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownAvailable']"));
            for (int j = 0; j < sizeList.size(); j++) {
                sizeList.get(j).click();
//                logger.info(driver.getPageSource());
                waitForLoad(driver);
                List<WebElement> colorList = driver.findElements(By.xpath("//div[@id='variation_color_name']/ul/li[@class='swatchAvailable']/span/div/span/span/span/button[@class='a-button-text']"));
                if(colorList != null && colorList.size() > 0) {
                    for (int k = 0; k < colorList.size(); k++) {
                        colorList.get(k).click();
                        waitForLoad(driver);
                        parseProduct(driver);
                    }
                }else{
                    parseProduct(driver);
                }
            }
            logger.info(driver.getTitle());
        }
    }

    public void parseProduct(WebDriver driver){
        WebElement productSize = driver.findElement(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownSelect']"));
        WebElement productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
        WebElement productTitle = driver.findElement(By.id("productTitle"));
        WebElement productPrice = null;
        try{
            productPrice = driver.findElement(By.id("priceblock_saleprice"));
        }catch(NoSuchElementException nsee){
            try{
                productPrice = driver.findElement(By.id("priceblock_ourprice"));
            }catch(NoSuchElementException nsee2){
                return;
            }
        }

        logger.info(productTitle.getText()+"|"+productSize.getText()+"|"+productColor.getText()+"|"+productPrice.getText());

    }
    public static void saveSnapshot(WebDriver driver){
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("screenshot_.png"));
        }catch(IOException ex){
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }
}
