package com.sumitcoder.service;

import com.sumitcoder.exception.CartItemException;
import com.sumitcoder.exception.UserException;
import com.sumitcoder.model.Cart;
import com.sumitcoder.model.CartItem;
import com.sumitcoder.model.Product;


public interface CartItemService {
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
