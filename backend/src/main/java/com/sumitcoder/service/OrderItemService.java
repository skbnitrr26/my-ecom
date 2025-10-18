package com.sumitcoder.service;


import com.sumitcoder.exception.OrderException;
import com.sumitcoder.model.OrderItem;
import com.sumitcoder.model.Product;

public interface OrderItemService {

	OrderItem getOrderItemById(Long id) throws Exception;
	


}
