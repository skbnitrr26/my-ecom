package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {

	SellerReport findBySellerId(Long sellerId);
}
