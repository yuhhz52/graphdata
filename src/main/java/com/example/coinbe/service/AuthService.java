package com.example.coinbe.service;

import com.example.coinbe.dto.request.LoginRequest;
import com.example.coinbe.dto.response.MessageResponse;
import com.example.coinbe.dto.response.TokenRefreshResponse;
import com.example.coinbe.dto.response.UserInfoResponse;
import com.example.coinbe.entity.RefreshToken;
import com.example.coinbe.exception.AppException;
import com.example.coinbe.exception.ErrorCode;
import com.example.coinbe.repository.RefreshTokenRepository;
import com.example.coinbe.security.jwt.JwtUtils;
import com.example.coinbe.security.service.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public UserInfoResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString());


        return new UserInfoResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getTitle()
        );
    }

    public MessageResponse logout(UUID userId, HttpServletResponse response) {
        refreshTokenRepository.deleteByUserId(userId);

        ResponseCookie cleanJwt = ResponseCookie.from("viegym-jwt", "")
                .path("/api")
                .httpOnly(true)
                .maxAge(0)
                .build();

        ResponseCookie cleanRefresh = ResponseCookie.from("viegym-jwt-refresh", "")
                .path("/api/auth")
                .httpOnly(true)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cleanJwt.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, cleanRefresh.toString());

        return new MessageResponse("Logout successful");
    }


    public TokenRefreshResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if (refreshToken != null && !refreshToken.isEmpty()) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String newAccessToken = jwtUtils.generateTokenFromUsername(user.getEmail());
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user.getEmail());
                        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

                        return TokenRefreshResponse.builder()
                                .accessToken(newAccessToken)
                                .refreshToken(refreshToken)
                                .build();
                    })
                    .orElseThrow(() -> new AppException(ErrorCode.TOKEN_REFRESH_FAILED));
        }

        throw new AppException(ErrorCode.TOKEN_REFRESH_FAILED);
    }

}