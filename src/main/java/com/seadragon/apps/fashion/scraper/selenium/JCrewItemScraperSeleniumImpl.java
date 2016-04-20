package com.seadragon.apps.fashion.scraper.selenium;

import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemImage;
import com.seadragon.apps.fashion.model.ItemPromotion;
import com.seadragon.apps.fashion.model.Product;
import com.seadragon.apps.fashion.model.ProductDetail;
import com.seadragon.apps.fashion.model.Promotion;
import com.seadragon.apps.fashion.scraper.Scraper;
import com.seadragon.apps.fashion.service.PromotionService;
import com.seadragon.apps.fashion.util.ExtractUtil;

@Service("jcrewItemScraper")
public class JCrewItemScraperSeleniumImpl implements Scraper<Item> {
	private Logger logger = LoggerFactory.getLogger(JCrewItemScraperSeleniumImpl.class);
	private String webSite = "jcrew";
	
	@Autowired
	private PromotionService promotionService;
	
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
	public Item scrape(WebDriver driver, String url) {
//		WebDriver driver = new FirefoxDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
	
		driver.get(url);
	
		List<WebElement> elements = null;
		Item item = new Item();
		item.setUrl(url);
		item.setWebSite(webSite);
		float originalPrice = 0.0f;
		try{
			Thread.sleep(500);
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
		}
		elements = driver.findElements(By.xpath("//div[@id='variants']"));
		String promoDesc = null;
		if (elements.size() > 0) {
			elements = driver.findElements(By.xpath("//div[@class='variant-wrapper']"));
			for (WebElement element : elements) {
				WebElement ele = element.findElement(By.xpath(".//input[@class='product-details-variants']"));
				if (ele.getAttribute("checked") != null) {
					item.setType(element.findElement(By.xpath(".//div[@class='product-pricing']/span[not(@class)]")).getText().trim());
					List<WebElement> eles = element.findElements(By
							.xpath(".//div[@class='product-pricing-wrapper']/span[contains(@class,'full-price')]"));
					if (eles.size() > 0) {
						originalPrice = ExtractUtil.stringToFloat(eles.get(0).getText());
					} else {
						eles = element.findElements(By.xpath(".//div[@class='product-pricing-wrapper']/span[@class='price-soldout']"));
						if (eles.size() > 0) {
							originalPrice = ExtractUtil.stringToFloat(eles.get(0).getText());
						}
					}
					eles = element.findElements(By.xpath(".//div[@class='categoryPromoPDP']"));
					if (eles.size() > 0) {
						promoDesc = eles.get(0).getText().trim();
					}
				}
			}
		} else {
			elements = driver.findElements(By.xpath("//div[@class='full-price']"));
			if (elements.size() > 0) {
				List<WebElement> priceSpanElements = elements.get(0).findElements(By.xpath(".//span"));
				if (priceSpanElements.size() > 0) {
					originalPrice = ExtractUtil.stringToFloat(priceSpanElements.get(0).getText());
				}
				List<WebElement> promoElements = elements.get(0).findElements(By.xpath(".//div[@class='categoryPromoPDP']"));
				if (promoElements.size() > 0) {
					promoDesc = elements.get(0).getText().trim();
				}
			}
		}

		item.setName(driver.findElement(By.xpath("//section/header/h1")).getText().trim());
		item.setNumber(driver.findElement(By.xpath("//span[@class='item-num']")).getText().replaceAll("item", "").trim());

		item.setDescription((String) (executor.executeScript("return arguments[0].innerHTML;",
				driver.findElement(By.xpath("//div[@class='product_desc']")))));

		List<WebElement> soldoutElements = driver.findElements(By
				.xpath("//div[@class='product-detail-sku']/descendant::div[@class='override-message']"));
		if (soldoutElements.size() > 0) {
			item.setAvailable(false);
		} else {
			// promotion handling
			if (promoDesc != null) {
				List<String> promoList = ExtractUtil.extractPromotion(promoDesc);
				if (promoList != null) {
					ItemPromotion itemPromotion = new ItemPromotion();
					if (item.getItemPromotions() == null) {
						item.setItemPromotions(new HashSet<ItemPromotion>());
					}
					item.getItemPromotions().add(itemPromotion);
					itemPromotion.setItem(item);
					itemPromotion.setDiscount((float) (Integer.parseInt(promoList.get(0)) * 0.01));
					String promotionCode = promoList.get(1);
					Promotion promotion = promotionService.findByPromotionCode(webSite, promotionCode);
					if (promotion == null) {
						promotion = new Promotion();
						promotion.setWebSite(webSite);
						promotion.setPromotionCode(promotionCode);
						driver.findElement(By.id("globalHeaderDetailsLink")).click();
						promotion.setDisclaimer(driver.findElement(By.id("globalHeaderDisclaimertext")).getText());
						List<String> promoDates = ExtractUtil.extractPromotionDates(promotion.getDisclaimer());
						if (promoDates != null) {
							promotion.setStartDate(promoDates.get(0));
							promotion.setEndDate(promoDates.get(1));
						}
						promotion = promotionService.save(promotion);
					}
					itemPromotion.setPromotion(promotion);
				}
			}

			// item images
			elements = driver.findElements(By.xpath("//div[@class='product-detail-img']/descendant::img[@class='product-detail-images']"));
			if (elements.size() > 0) {
				for (WebElement ele : elements) {
					ItemImage itemImage = new ItemImage();
					itemImage.setBigImageUrl(ele.getAttribute("data-imgurl"));
					itemImage.setSmallImageUrl(ele.getAttribute("src"));
					itemImage.setItem(item);
					if (item.getItemImages() == null) {
						item.setItemImages(new HashSet<ItemImage>());
					}
					item.getItemImages().add(itemImage);
				}
			}

			elements = driver.findElements(By.xpath("//div[@class='price-wrapper']/section[contains(@class,'color-row')]"));
			String color = null;
			String colorCode = null;
			float price = 0;
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					List<WebElement> priceElements = driver.findElements(By
							.xpath("//div[@class='price-wrapper']/div[contains(@class, 'product-detail-price')]"));
					if (priceElements.size() > 0) {
						price = ExtractUtil.stringToFloat(priceElements.get(i).getText());
					} else {
						price = originalPrice;
					}
					List<WebElement> elements2 = elements.get(i).findElements(By.xpath(".//div[contains(@class, 'color-box')]"));
					for (WebElement element2 : elements2) {
						colorCode = element2.getAttribute("data-color");
						element2.findElement(By.xpath(".//a[@id='" + colorCode + "']")).click();
						color = driver.findElement(By.xpath("//div[@class='color-title']/span[@class='color-name']")).getText();
						List<WebElement> sizeElements = driver.findElements(By
								.xpath("//section[@class='sizes']/div[contains(@class, 'size-box')]"));
						if (sizeElements.size() > 0) {
							for (WebElement sizeElement : sizeElements) {
								Product product = new Product();
								item.getProducts().add(product);
								product.setColor(color);
								product.setColorCode(colorCode);
								product.setOriginalPrice(originalPrice);
								product.setItem(item);
								product.setProductSize(sizeElement.getAttribute("data-size"));
								ProductDetail productDetail = new ProductDetail();
								product.getProductDetails().add(productDetail);
								productDetail.setPrice(price);
								productDetail.setProduct(product);
								if (sizeElement.getAttribute("class").contains("unavailable")) {
									productDetail.setAvailable(false);
								} else {
									productDetail.setAvailable(true);
								}
							}
						}
					}
				}
			}
		}
		return item;
	}
}
