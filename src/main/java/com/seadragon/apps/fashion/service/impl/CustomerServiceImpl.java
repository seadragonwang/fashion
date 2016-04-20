package com.seadragon.apps.fashion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seadragon.apps.fashion.model.Customer;
import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.repository.CustomerRepository;
import com.seadragon.apps.fashion.service.CustomerService;
import com.seadragon.apps.fashion.service.ItemService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemService itemService;

	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}


	@Override
	public Customer findById(long id) {
		return customerRepository.findOne(id);
	}

	@Override
	public Page<Customer> findAll(Pageable page) {
		return customerRepository.findAll(page);
	}

	@Override
	public void subscribe(Customer customer, Item item) {
		if(item == null){
			logger.error("The item doesn't exist.");
		}
		customer.getItems().add(item);
		item.getCustomers().add(customer);
		customerRepository.saveAndFlush(customer);
	}
}
