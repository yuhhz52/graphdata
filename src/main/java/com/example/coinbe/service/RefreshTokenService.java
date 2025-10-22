package com.example.coinbe.service;

import com.example.coinbe.entity.RefreshToken;
import com.example.coinbe.entity.User;
import com.example.coinbe.exception.AppException;
import com.example.coinbe.exception.ErrorCode;
import com.example.coinbe.repository.RefreshTokenRepository;
import com.example.coinbe.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${coin.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .token(UUID.randomUUID().toString())
                .isRevoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new AppException(ErrorCode.TOKEN_REFRESH_FAILED);

        }
        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        // Kiểm tra user có tồn tại không
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return refreshTokenRepository.deleteByUserId(userId);
    }


}