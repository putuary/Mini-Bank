package com.miniBank.service;

import com.miniBank.model.entity.User;
import com.miniBank.model.request.AuthRequest;
import com.miniBank.model.request.LoginRequest;
import com.miniBank.model.response.LoginResponse;

public interface AuthService {
    User registerEmployee(AuthRequest authRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
}
