package com.sumitcoder.service;

import com.sumitcoder.exception.ProductException;
import com.sumitcoder.model.Cart;
import com.sumitcoder.model.CartItem;
import com.sumitcoder.model.Product;
import com.sumitcoder.model.User;
import com.sumitcoder.request.AddItemRequest;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	
	public Cart findUserCart(User user);

}
