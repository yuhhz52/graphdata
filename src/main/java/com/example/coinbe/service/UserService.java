package com.example.coinbe.service;

import com.example.coinbe.dto.request.UserCreationRequest;
import com.example.coinbe.dto.response.UserResponse;


public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    UserResponse getCurrentUser();


}