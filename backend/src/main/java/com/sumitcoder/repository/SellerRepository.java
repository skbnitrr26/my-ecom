package com.sumitcoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.domain.AccountStatus;
import com.sumitcoder.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {

	Seller findByEmail(String email);
	
	List<Seller> findByAccountStatus(AccountStatus status);
}
