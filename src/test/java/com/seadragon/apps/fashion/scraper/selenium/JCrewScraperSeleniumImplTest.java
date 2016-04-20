package com.seadragon.apps.fashion.scraper.selenium;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.scraper.Scraper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/fashion-application-context-test.xml"})
public class JCrewScraperSeleniumImplTest {
//	@Autowired
//	private Scraper<Item> jcrewScraperSelenium;
//	@Test
//	public void testScrapeWithThreeTypes() {
//		Item item = jcrewScraperSelenium.scrape("jcrew", "https://www.jcrew.com/AST/Browse/WomenBrowse/Women_sale_events/30off/shirtsandtops/PRDOVR~A1312/A1312.jsp");
//		assertNotNull(item);
//	}
//	@Test
//	public void testScrapeWithoutTypes() {
//		Item item = jcrewScraperSelenium.scrape("jcrew", "https://www.jcrew.com/AST/Browse/CrewCutBrowse/Girls_Sale_Events/30off/knitstees/PRDOVR~A3978/A3978.jsp");
//		assertNotNull(item);
//	}
	
}
