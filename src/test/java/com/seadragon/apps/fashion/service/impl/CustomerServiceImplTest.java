package com.seadragon.apps.fashion.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seadragon.apps.fashion.model.Customer;
import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.service.CustomerService;
import com.seadragon.apps.fashion.service.ItemService;
import com.seadragon.apps.fashion.service.ScrapeService;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/fashion-application-context-test.xml"})
public class CustomerServiceImplTest {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ScrapeService service;

	@Transactional
	@Test
	public void testSave() {
		Customer customer = customerService.findByEmail("wang.seadragon@gmail.com");
		Item item = new Item();
		item.setUrl("https://www.jcrew.com/womens_category/sweaters/jcrewcashmere/PRD~47920/47920.jsp?Nbrd=J&Nloc=en_US&Nrpp=48&Npge=1&Ntrm=Collection%20cashmere%20boyfriend%20V-neck%20sweater&isFromSearch=true&isNewSearch=true");
		item.setWebSite("jcrew");
		customer.getItems().add(item);
		customerService.save(customer);
	}

	@Test
	public void testSubscribe() {
		Customer customer = customerService.findByEmail("wang.seadragon@gmail.com");
		Item item = itemService.findById(8);
		customerService.subscribe(customer, item);
	}

}
