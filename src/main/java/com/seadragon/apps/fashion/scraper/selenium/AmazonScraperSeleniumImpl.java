package com.seadragon.apps.fashion.scraper.selenium;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.scraper.Scraper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hwang001c on 5/7/16.
 */
public class AmazonScraperSeleniumImpl implements Scraper<Item> {
    private static final Logger logger = LoggerFactory.getLogger(AmazonScraperSeleniumImpl.class);

    @Override
    public Item scrape(WebDriver driver, String url) {

        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//        String url = "http://www.amazon.com/Newport-H2-Sandal-Toddler-Gargoyle/dp/B00DGBMI50/ref=sr_1_8?s=apparel&ie=UTF8&qid=1462548227&sr=1-8&nodeID=679182011&keywords=shoes+for+boys&refinements=p_n_size_four_browse-vebin%3A3491794011";
        driver.navigate().to("http://www.amazon.com");
        WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Go']"));

        searchTextBox.sendKeys("shoes for boys");
        submitButton.click();
//        driver.findElement(By.cssSelector("a[href*='Boys']")).click();
        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@id='s-results-list-atf']/li[contains(@class, 's-result-item')]/div[@class='s-item-container']/div/div/div/span/div/a[contains(@class, 'a-link-normal')]"));

        String base = driver.getWindowHandle();
        for (int i = 0; i < linkList.size(); i++) {
            Actions newTab = new Actions(driver);
//            newTab.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).click(linkList.get(i)).keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).build().perform();
//            Thread.sleep(5000);
            newTab.keyDown(Keys.LEFT_CONTROL).keyDown(Keys.LEFT_SHIFT).perform();

// Wrap in a try/catch during implementation to ensure you perform keyUp(s).
            linkList.get(i).click();

            newTab.keyUp(Keys.LEFT_CONTROL).keyDown(Keys.LEFT_SHIFT).perform();
            //handle windows change
            Set<String> set = driver.getWindowHandles();

//            driver.switchTo().window((String) set.toArray()[0]);

            //close the window and sitch back to the base tab


            List<WebElement> sizeList = driver.findElements(By.xpath("//select[@id='native_dropdown_selected_size_name']/option[@class='dropdownAvailable']"));
            for (int j = 0; j < sizeList.size(); j++) {
                sizeList.get(j).click();
                List<WebElement> colorList = driver.findElements(By.xpath("//div[@id='variation_color_name']/ul/li[@class='swatchAvailable']/span/div/span/span/span/button[@class='a-button-text']"));
                for (int k = 0; k < colorList.size(); k++) {
                    colorList.get(k).click();
//                    Thread.sleep(2000);
                    WebElement productColor = driver.findElement(By.xpath("//div[@id='variation_color_name']/div/span"));
                    WebElement productTitle = driver.findElement(By.id("productTitle"));
                    WebElement productPrice = null;
                    try {
                        productPrice = driver.findElement(By.xpath("//div[@id='price_feature_div']/div[@id='price']/table/tbody/tr/td/span[@id='priceblock_ourprice']"));
                    }catch(NoSuchElementException nsee){
                        continue;
                    }
                    String productSize = sizeList.get(j).getText();
                    logger.info(productTitle.getText() + "|" + productSize + "|" + productColor.getText() + "|" + productPrice.getText());
                }
            }
            newTab.keyUp(Keys.LEFT_SHIFT).keyDown(Keys.LEFT_CONTROL).sendKeys("w").perform();
            driver.switchTo().window(base);
        }
        return null;
    }

}
