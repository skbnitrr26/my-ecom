package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Coupon findByCode(String couponCode);
}
