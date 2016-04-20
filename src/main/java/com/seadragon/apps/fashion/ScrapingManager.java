package com.seadragon.apps.fashion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.scraper.Scraper;
import com.seadragon.apps.fashion.service.ItemService;

@Service
public class ScrapingManager {
//	@Autowired
//	private Scraper<Item> jcrewScraper;
//	
//	@Autowired
//	ItemService itemService;
//
//	public void scrape(int pageSize){
//		long count = itemService.count();
//		for(int index = 0; (index+1)*pageSize < count; index++){
//			Page<Item> items = itemService.findAll(new PageRequest(index, pageSize));
//			for(Item item : items){
//				if("jcrew".equalsIgnoreCase(item.getWebSite())){
//					Item newItem = jcrewScraper.scrape(item.getUrl());
//					
//				}
//			}
//		}
//	}
//	public static final void main(String[] argv){
//		
//	}
}
