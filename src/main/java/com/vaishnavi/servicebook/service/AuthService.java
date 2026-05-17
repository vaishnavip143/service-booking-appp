package com.vaishnavi.servicebook.service;

import com.vaishnavi.servicebook.dto.LoginDto;
import com.vaishnavi.servicebook.dto.RegisterDto;
import com.vaishnavi.servicebook.dto.ApiResponse;

public interface AuthService {
    ApiResponse<?> register(RegisterDto registerDto);

    ApiResponse<?> login(LoginDto loginDto);
}
