package com.seadragon.apps.fashion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemImage;
import com.seadragon.apps.fashion.model.ItemPromotion;
import com.seadragon.apps.fashion.model.Product;
import com.seadragon.apps.fashion.model.ProductDetail;
import com.seadragon.apps.fashion.scraper.Scraper;
import com.seadragon.apps.fashion.service.ItemService;
import com.seadragon.apps.fashion.service.ScrapeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ScrapeServiceImpl implements ScrapeService{
	private static Logger logger = LoggerFactory.getLogger(ScrapeServiceImpl.class);
	
	private List<Scraper<List<Item>>> saleScrapers;
	
	private Map<String, Scraper<Item>> itemScraperMap;

	@Autowired
	private ItemService itemService;
	
	public List<Scraper<List<Item>>> getSaleScrapers() {
		return saleScrapers;
	}

	public void setSaleScrapers(List<Scraper<List<Item>>> saleScrapers) {
		this.saleScrapers = saleScrapers;
	}

	public Map<String, Scraper<Item>> getItemScraperMap() {
		return itemScraperMap;
	}

	public void setItemScraperMap(Map<String, Scraper<Item>> itemScraperMap) {
		this.itemScraperMap = itemScraperMap;
	}

	public Item scrape(WebDriver driver, String webSite, String url) {
		if(itemScraperMap.get(webSite) != null){
			return itemScraperMap.get(webSite).scrape(driver, url);
		}else{
			logger.info("There is no scraper for web site : " + webSite);
			return null;
		}
	}

	public List<Item> scrapeSale(WebDriver driver) {
		List<Item> results = new ArrayList<Item>();
		for(Scraper<List<Item>> scraper : saleScrapers){
			List<Item> items = scraper.scrape(driver, null);
			results.addAll(itemService.save(items));
		}
		return results;
	}

	@Transactional
	public boolean scrape(WebDriver driver, Pageable request) {
		Page<Item> page = itemService.findAll(request);
		boolean changed = false;
		for(Item item : page){
			changed = false;
			Item newItem = scrape(driver, item.getWebSite(), item.getUrl());
			logger.info("Scraping " + item.getNumber() + ", " + item.getName() + ": " + item.getUrl());
			if(item.getProducts() == null || item.getProducts().size() == 0){
				if(newItem.getProducts() != null && newItem.getProducts().size() > 0){
					if(item.getProducts() == null){
						item.setProducts(new ArrayList<Product>());
					}
				}
				item.getProducts().addAll(newItem.getProducts());
				item.setName(newItem.getName());
				item.setNumber(newItem.getNumber());
				item.setDescription(newItem.getDescription());
				item.setDateUpdated(new Date());
				if(item.getItemImages() == null){
					item.setItemImages(new HashSet<ItemImage>());
				}
				item.getItemImages().addAll(newItem.getItemImages());
				if(item.getItemPromotions() == null){
					item.setItemPromotions(new HashSet<ItemPromotion>());
				}
				item.getItemPromotions().addAll(newItem.getItemPromotions());
			}else{
				if(newItem.getProducts() != null && newItem.getProducts().size() > 0){
					for(Product p1 : newItem.getProducts()){
						for(Product p2 : item.getProducts()){
							if(p1.getColorCode().equalsIgnoreCase(p2.getColorCode()) && p1.getProductSize().equalsIgnoreCase(p2.getProductSize())){
								ProductDetail pd1 = p1.getProductDetails().get(0);
								ProductDetail pd2 = p2.getProductDetails().get(0);

								if(pd1.getPrice() != pd2.getPrice() || (pd1.isAvailable() ^ pd2.isAvailable())){
									pd1.setProduct(p2);
									p2.getProductDetails().add(0, pd1);
									changed = true;
								}
								break;
							}
						}
					}
				}
			}
			if(changed){
				logger.info("This item got changed: " + item.getNumber() + ", " + item.getName());
				itemService.update(item);
			}
		}
		return page.hasNext();
	}
}
