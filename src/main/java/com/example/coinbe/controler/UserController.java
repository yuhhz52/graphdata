package com.example.coinbe.controler;

import com.example.coinbe.dto.request.UserCreationRequest;
import com.example.coinbe.dto.response.ApiResponse;
import com.example.coinbe.dto.response.UserResponse;
import com.example.coinbe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }


    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getCurrentUser())
                .build();
    }





}