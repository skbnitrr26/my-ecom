package com.sumitcoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findBySellerId(Long sellerId);
}
