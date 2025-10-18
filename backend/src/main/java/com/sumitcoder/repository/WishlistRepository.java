package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	Wishlist findByUserId(Long userId);
}
