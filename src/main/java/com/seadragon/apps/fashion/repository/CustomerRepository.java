package com.seadragon.apps.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seadragon.apps.fashion.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findByEmail(String email);
}
