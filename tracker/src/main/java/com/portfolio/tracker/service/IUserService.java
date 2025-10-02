package com.portfolio.tracker.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.portfolio.tracker.dto.request.UserRequestDto;
import com.portfolio.tracker.dto.response.UserResponse;

public interface IUserService extends UserDetailsService {
    UserResponse createUser(UserRequestDto userRequestDto);
    UserResponse signin(UserRequestDto userRequestDto);
}
