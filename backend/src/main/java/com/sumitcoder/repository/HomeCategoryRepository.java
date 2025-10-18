package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.HomeCategory;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {

	
}
