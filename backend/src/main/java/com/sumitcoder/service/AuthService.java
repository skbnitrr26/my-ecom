package com.sumitcoder.service;

import com.sumitcoder.exception.SellerException;
import com.sumitcoder.exception.UserException;
import com.sumitcoder.request.LoginRequest;
import com.sumitcoder.request.ResetPasswordRequest;
import com.sumitcoder.request.SignupRequest;
import com.sumitcoder.response.ApiResponse;
import com.sumitcoder.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
