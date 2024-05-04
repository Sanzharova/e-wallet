package com.example.ecomerseshop.service;

import com.example.ecomerseshop.entity.RefreshTokenEntity;
import com.example.ecomerseshop.entity.UserEntity;
import com.example.ecomerseshop.repository.RefreshTokenRepository;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshTokenDurationMs;


    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtils = jwtUtils;
    }


    public void deleteById(int id) {
        refreshTokenRepository.deleteById(id);
    }


    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        var refreshTokens = jwtUtils.generateRefreshToken(user);

        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(refreshTokens);
        refreshToken.setRefreshCount(0);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setUpdatedAt(Instant.now());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }


}