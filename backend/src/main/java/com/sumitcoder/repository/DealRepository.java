package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {

}
