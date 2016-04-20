package com.seadragon.apps.fashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seadragon.apps.fashion.model.Item;

public interface ItemService {
	Item save(Item item);
	void update(Item item);
//	Page<Item> findAllByCustomer(Customer customer, int pageIndex, int pageSize);
//	long countByCustomer(Customer customer);
	long count();
	Page<Item> findAll(Pageable page);
	Item findByUrl(String url);
	Item findById(long id);
	List<Item> save(List<Item> items);
}
