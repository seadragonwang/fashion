package com.seadragon.apps.fashion.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seadragon.apps.fashion.model.Customer;
import com.seadragon.apps.fashion.model.Item;

public interface CustomerService {
	void save(Customer customer);
	Customer findByEmail(String email);
	Customer findById(long id);
	void subscribe(Customer customer, Item item);
	Page<Customer> findAll(Pageable page);
}
