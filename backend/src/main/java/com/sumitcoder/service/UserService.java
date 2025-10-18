package com.sumitcoder.service;

import java.util.List;

import com.sumitcoder.exception.UserException;
import com.sumitcoder.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;


}
