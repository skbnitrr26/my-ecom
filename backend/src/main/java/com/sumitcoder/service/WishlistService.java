package com.sumitcoder.service;


import com.sumitcoder.exception.WishlistNotFoundException;
import com.sumitcoder.model.Product;
import com.sumitcoder.model.User;
import com.sumitcoder.model.Wishlist;

import java.util.Optional;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

