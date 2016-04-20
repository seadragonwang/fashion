package com.seadragon.apps.fashion.service;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.data.domain.Pageable;

import com.seadragon.apps.fashion.model.Item;

public interface ScrapeService {
	Item scrape(WebDriver driver, String webSite, String url);
	List<Item> scrapeSale(WebDriver driver);
	boolean scrape(WebDriver driver, Pageable request);
}
