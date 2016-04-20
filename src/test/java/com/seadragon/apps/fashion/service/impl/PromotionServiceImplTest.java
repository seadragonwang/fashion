package com.seadragon.apps.fashion.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seadragon.apps.fashion.model.Promotion;
import com.seadragon.apps.fashion.service.PromotionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/fashion-application-context-test.xml" })
public class PromotionServiceImplTest {

	@Autowired
	private PromotionService promotionService;
	
	@Test
	@Rollback(false)
	public void testSave() {
		Promotion promotion = new Promotion();
		promotion.setPromotionCode("FRESHSALE");
		promotion.setDisclaimer("ONLINE: 30% OFF SALE, 50% OFF FINAL SALE. IN STORES: " +
				"40% OFF SALE. 30% discount valid on the purchase of sale items at jcrew.com. " +
				"50% discount valid on the purchase of final sale items listed in the 50% Off " +
				"Final Sale Styles assortment under the Sale tab at jcrew.com. 40% discount " +
				"valid on the purchase of sale items in J.Crew stores. " +
				"Offers valid from April 10, 2014, 12:01am ET through April 14, 2014, 11:59pm ET. " +
				"Offers not valid in Liquor Store or Ludlow Shops; in 769 Madison Avenue, " +
				"Bloor Street or J.Crew Factory stores; at jcrewfactory.com; or on phone orders. " +
				"Offers cannot be applied to previous purchases or the purchase of gift cards and " +
				"cannot be redeemed for cash or used in combination with any other offer. In-store " +
				"discount automatically taken at the register. To redeem online, enter code FRESHSALE " +
				"in the promo code box at checkout. Limit one promotional code per order. Offers " +
				"valid in the U.S. and Canada only. Terms of offers are subject to change. Final sale items cannot be returned or exchanged.");
		promotion.setStartDate("April 7, 2014, 12:01am ET");
		promotion.setEndDate("April 10, 2014, 11:59pm ET");
		promotion.setWebSite("jcrew");
		promotionService.save(promotion);
	}

	@Test
	public void testFindByPromotionCode() {
		Promotion promotion = promotionService.findByPromotionCode("jcrew", "FRESHSALE");
		
		assertNotNull(promotion);
	}

}
