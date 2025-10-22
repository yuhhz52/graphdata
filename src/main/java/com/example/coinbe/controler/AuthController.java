package com.example.coinbe.controler;

import com.example.coinbe.dto.request.LoginRequest;
import com.example.coinbe.dto.response.ApiResponse;
import com.example.coinbe.dto.response.MessageResponse;
import com.example.coinbe.dto.response.TokenRefreshResponse;
import com.example.coinbe.dto.response.UserInfoResponse;
import com.example.coinbe.security.service.UserDetailsImpl;
import com.example.coinbe.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ApiResponse<UserInfoResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                               HttpServletResponse response) {
        return ApiResponse.<UserInfoResponse>builder()
                .result(authService.login(loginRequest, response))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<MessageResponse> logout(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               HttpServletResponse response) {
        return ApiResponse.<MessageResponse>builder()
                .result(authService.logout(userDetails.getId(), response))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenRefreshResponse> refreshToken(HttpServletRequest request,
                                                          HttpServletResponse response) {
        return ApiResponse.<TokenRefreshResponse>builder()
                .result( authService.refreshToken(request, response))
                .build();

    }
}