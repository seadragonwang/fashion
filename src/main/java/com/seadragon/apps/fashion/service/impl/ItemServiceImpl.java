package com.seadragon.apps.fashion.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.repository.ItemRepository;
import com.seadragon.apps.fashion.service.ItemService;

@Service("itemService")
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public Item save(Item item) {
		return itemRepository.save(item);			
	}

	@Override
	public void update(Item item) {
		itemRepository.save(item);
	}

//	@Override
//	public Page<Item> findAllByCustomer(Customer customer, int pageIndex, int pageSize) {
//		return itemRepository.findAll(new PageRequest(pageIndex, pageSize));
//	}
//
//	@Override
//	public long countByCustomer(Customer customer){
//		return itemRepository.countByCustomer(customer);
//	}

	@Override
	public List<Item> save(List<Item> items){
		return itemRepository.save(items);
	}
	@Override
	public long count() {
		return itemRepository.count();
	}

	@Override
	public Page<Item> findAll(Pageable page) {
		return itemRepository.findAll(page);
	}

	public Item findByUrl(String url) {
		return itemRepository.findByUrl(url);
	}

	@Override
	public Item findById(long id) {
		return itemRepository.findOne(id);
	}

}
