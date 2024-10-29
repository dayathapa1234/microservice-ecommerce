package com.ecommerce.account.service;

import com.ecommerce.account.response.SignupRequest;

public interface AuthService {

    String createUSer(SignupRequest request);
}
