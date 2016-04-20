package com.seadragon.apps.fashion.scraper.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.scraper.Scraper;

public class JCrewSaleScraperSeleniumImpl implements Scraper<List<Item>> {
	private static Logger logger = LoggerFactory.getLogger(JCrewSaleScraperSeleniumImpl.class);
	
	private String webSite = "jcrew";
	private String url;
	
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Item> scrape(WebDriver driver, String urlString) {
		// WebDriver driver = new FirefoxDriver();
		driver.get(url);
		List<WebElement> categories = driver.findElements(By.xpath("//nav[@id='selectCategory']/a"));
		List<Item> results = new ArrayList<Item>();
		if (categories.size() > 0) {
			boolean hasNext = false;
			List<String> categoryUrls = new ArrayList<String>();
			for (WebElement category : categories) {
				categoryUrls.add(category.getAttribute("href"));
			}
			for (int i = 0; i < categoryUrls.size(); i++) {
				String categoryUrl = categoryUrls.get(i);
				boolean view100 = false;
				driver.get(categoryUrl);
				try{
					Thread.sleep(500);
				}catch(Exception ex){
					logger.error(ex.getMessage(), ex);
				}
				do {
					if (!view100) {
						List<WebElement> viewElements = driver.findElements(By.xpath("//span[@class='searchSaleResultsPerPageOption']"));
						if(viewElements.size() > 0){
							if(viewElements.get(0).isDisplayed()){
								viewElements.get(0).click();
							}
						}
						view100 = true;
						try{
							Thread.sleep(1000);
						}catch(Exception ex){
							logger.error(ex.getMessage(), ex);
						}
					}
					List<WebElement> items = driver.findElements(By.xpath("//section[@id='searchSaleProductSection']/figure[contains(@class, 'product-item')]/a"));
					if (items.size() > 0) {
						for (WebElement item : items) {
							try {
								results.add(new Item(webSite, item.getAttribute("href")));
							} catch (Exception ex) {
								logger.error(ex.getMessage(), ex);
							}
						}
					}
					WebElement nextBtn = null;
					while(nextBtn == null){
						try {
							nextBtn = driver.findElement(By.xpath("//div[@id='paginationNextDiv']/a[@id='paginationBtmNext']"));
						} catch (Exception ex) {
							logger.error(ex.getMessage(), ex);
	//						driver.navigate().back();
	//						nextBtn = driver.findElement(By.xpath("//div[@id='paginationNextDiv']/a[@id='paginationBtmNext']"));
						}
						
					}
					if (nextBtn.getAttribute("class").indexOf("disabled") < 0) {
						hasNext = true;
						nextBtn.click();
						try{
							Thread.sleep(2000);
						}catch(Exception ex){
							logger.error(ex.getMessage(), ex);
						}
					} else {
						hasNext = false;
					}
				} while (hasNext);

			}
		}
		return results;
	}
}
