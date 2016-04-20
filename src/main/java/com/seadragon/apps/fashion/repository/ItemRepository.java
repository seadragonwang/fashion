package com.seadragon.apps.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seadragon.apps.fashion.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
//	@Query("select a from item a join a.customers c where c.customer.id=:customerId")
//	List<Item> findByCustomer(@Param("customerId")Long customerId);
//
//	@Query("select count(it) from item it join it.customers c where c.customer.id=:customerId")
//	int countByCustomer(@Param("customerId")Long customerId);
	
	Item findByUrl(String url);
}
