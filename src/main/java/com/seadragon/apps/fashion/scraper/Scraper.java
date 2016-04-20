package com.seadragon.apps.fashion.scraper;

import org.openqa.selenium.WebDriver;

public interface Scraper <T> {
	T scrape(WebDriver driver, String url);
}
