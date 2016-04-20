package com.seadragon.apps.fashion.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemPromotion;
import com.seadragon.apps.fashion.model.Promotion;
import com.seadragon.apps.fashion.scraper.Scraper;
import com.seadragon.apps.fashion.service.ItemService;
import com.seadragon.apps.fashion.service.PromotionService;
import com.seadragon.apps.fashion.service.ScrapeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/fashion-application-context-test.xml"})
public class ItemServiceImplTest {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ScrapeService scrapeService;

	@Autowired
	private Scraper<Item> jcrewItemScraper;

	@Autowired
	private PromotionService promotionService;

	@Test
	public void testSave() {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		Item item = scrapeService.scrape(driver, "jcrew", "https://www.jcrew.com/womens_category/pants/Skinny/PRDOVR~A1448/A1448.jsp");
		
		itemService.save(item);
		assertTrue(item.getId() > 0);
	}
	
	@Test
	public void testSavePromotion(){
		Item item = itemService.findById(5);
		ItemPromotion itemPromotion = new ItemPromotion();
		Promotion promotion = promotionService.findByPromotionCode("jcrew", "GOSHOP");
		itemPromotion.setDiscount(0.5f);
		itemPromotion.setItem(item);
		item.getItemPromotions().add(itemPromotion);
		itemPromotion.setPromotion(promotion);
		item.getItemPromotions().add(itemPromotion);
		itemService.update(item);
	}
}
