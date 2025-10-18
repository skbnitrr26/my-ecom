package com.sumitcoder.service;

import com.sumitcoder.model.VerificationCode;

public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);
}
