package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Cart;
import com.sumitcoder.model.CartItem;
import com.sumitcoder.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
