package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	
}
