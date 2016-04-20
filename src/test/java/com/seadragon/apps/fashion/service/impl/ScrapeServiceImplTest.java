package com.seadragon.apps.fashion.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemImage;
import com.seadragon.apps.fashion.scraper.Scraper;
import com.seadragon.apps.fashion.scraper.httpclient.JCrewScraper;
import com.seadragon.apps.fashion.service.ScrapeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/fashion-application-context-test.xml"})
public class ScrapeServiceImplTest {
//	@Autowired
//	private ScrapeService service;
//	
//	@Autowired
//	private Scraper jcrewScraper;
//	
//	//https://www.jcrew.com/womens_feature/NewArrivals/Cashmere/PRDOVR~47920/47920.jsp
//	//https://www.jcrew.com/womens_feature/NewArrivals/pants/PRDOVR~A5022/A5022.jsp
//
//	@Test
//	public void testScrape() {
//		Item item = service.scrape("jcrew", new JCrewScraper(), "https://www.jcrew.com/womens_category/pants/Skinny/PRDOVR~A1448/A1448.jsp");
//		assertTrue(item.getDescription().equals("Our classic Minnie pant updated in a foulard print, with the same sleek, slim fit and chic cropped leg as the original. <ul><li>Sits above hip.</li><li>Slim through hip and thigh, with a skinny, cropped leg.</li><li>26\" inseam.</li><li>Cotton with a hint of stretch.</li><li>Side zip.</li><li>Machine wash.</li><li>Import.</li></ul>"));
//		assertTrue(item.getName().equals("Minnie pant in foulard"));
//		assertTrue(item.getNumber().equals("A1448"));
//		assertTrue(item.getType().equals("Regular"));
//		assertEquals(item.getItemImages().size(), 3);
//		assertEquals(item.getProducts().size(), 10);
//	}
//	
//	@Test
//	public void testScrapeWithSoldPriceAndMultipleColors() {
//		Item item = service.scrape("jcrew", new JCrewScraper(), "https://www.jcrew.com/mens_category/shirts/washedfavoriteshirts/PRD~31187/99102125954/31187.jsp?N=21+16&Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Nsrt=3&isFromSale=true&isNewSearch=true");
//		System.out.println(item.getDescription());
//	}
//	// https://www.jcrew.com/AST/Browse/WomenBrowse/Women_sale_events/30off/shirtsandtops/PRDOVR~A1312/A1312.jsp
//	// https://www.jcrew.com/mens_feature/tobedeleted/catalogjcrewcomexclusives/pants/PRD~32756/99102160688/32756.jsp?N=21+16&Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Nsrt=3&isFromSale=true&isSaleItem=true&isNewSearch=true
//	@Test
//	public void testScrapeWithPromotionCodeAndDiscount() {
//		Item item = service.scrape("jcrew", jcrewScraper, "https://www.jcrew.com/womens_special_shops/resortshop/shirts/PRD~A1312/A1312.jsp?Nbrd=J&Nloc=en&Nrpp=48&Npge=1&Ntrm=SILK+DASH-DOT+BLOUSE&isFromSearch=true&isSaleItem=false&isNewSearch=true");
//		System.out.println(item.getDescription());
//	}
//	
//	@Test
//	public void testScrapeWithNoItemType() {
//		Item item = service.scrape("jcrew", new JCrewScraper(), "https://www.jcrew.com/womens_category/sweaters/jcrewcashmere/PRD~47920/47920.jsp?Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Ntrm=Collection%20cashmere%20boyfriend%20V-neck%20sweater&isFromSearch=true&isNewSearch=true");
//		assertTrue(item.getDescription().equals("Just because winter's over doesn't mean you have to say good-bye to cashmere. Slightly oversize (so there's more to love), we made this one just for you so you can stop stealing his. We work with one of the best Italian mills to create our famously soft cashmereâtreat yours with love and it will last forever. <ul><li>Loose fit.</li><li>Hits at hip.</li><li>Italian cashmere in a 12-gauge knit.</li><li>Long sleeves.</li><li>Hand wash.</li><li>We recommend folding (never hanging) and storing in tissue paper.</li><li>Import.</li><li>Online only.</li></ul>"));
//		assertTrue(item.getName().equals("Collection cashmere boyfriend V-neck sweater"));
//		assertTrue(item.getNumber().equals("47920"));
//		assertNull(item.getType());
//		assertEquals(3, item.getItemImages().size());
//		for(ItemImage image : item.getItemImages()){
//			assertTrue("https://s7.jcrew.com/is/image/jcrew/47920_NA6660_m?$pdp_tn75$".equals(image.getSmallImageUrl()) || 
//				"https://s7.jcrew.com/is/image/jcrew/47920_NA6660_b?$pdp_tn75$".equals(image.getSmallImageUrl()) ||
//				"https://s7.jcrew.com/is/image/jcrew/47920_NA6660_s?$pdp_tn75$".equals(image.getSmallImageUrl()));
//				
//			assertTrue("https://s7.jcrew.com/is/image/jcrew/47920_NA6660_m?$pdp_fs418$".equals(image.getBigImageUrl()) ||
//			   "https://s7.jcrew.com/is/image/jcrew/47920_NA6660_b?$pdp_fs418$".equals(image.getBigImageUrl()) ||
//			   "https://s7.jcrew.com/is/image/jcrew/47920_NA6660_s?$pdp_fs418$".equals(image.getBigImageUrl()));
//		}
//	}
//
}
