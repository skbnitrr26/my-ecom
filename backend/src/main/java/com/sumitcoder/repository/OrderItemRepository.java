package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.Order;
import com.sumitcoder.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
