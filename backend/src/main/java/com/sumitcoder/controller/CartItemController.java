package com.sumitcoder.controller;

import com.sumitcoder.exception.CartItemException;
import com.sumitcoder.exception.UserException;

import com.sumitcoder.model.CartItem;
import com.sumitcoder.model.User;
import com.sumitcoder.response.ApiResponse;
import com.sumitcoder.service.CartItemService;
import com.sumitcoder.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	private CartItemService cartItemService;
	private UserService userService;
	
	public CartItemController(CartItemService cartItemService, UserService userService) {
		this.cartItemService=cartItemService;
		this.userService=userService;
	}
	

}

