package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dto.*;
import com.example.ecomerseshop.entity.RefreshTokenEntity;
import com.example.ecomerseshop.entity.UserDeviceEntity;
import com.example.ecomerseshop.entity.UserEntity;
import com.example.ecomerseshop.entity.UserRole;
import com.example.ecomerseshop.exception.UserLoginException;
import com.example.ecomerseshop.repository.RefreshTokenRepository;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;


@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final UserDeviceService userDeviceService;

    private final RefreshTokenRepository refreshTokenRepository;


    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtils jwtUtils,
                                 AuthenticationManager authenticationManager,
                                 RefreshTokenService refreshTokenService,
                                 UserDeviceService userDeviceService,
                                 RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.userDeviceService = userDeviceService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthenticationResponse registrationUser(RegistrationRequest registerRequest) {

        UserEntity user = new UserEntity();


        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setCreatedAt(Timestamp.from(Instant.now()).toLocalDateTime());
        user.setIsAccountExpired(Boolean.TRUE);
        user.setIsActive(Boolean.TRUE);
        user.setIsAccountLocked(Boolean.TRUE);
        user.setIsEnabled(Boolean.TRUE);

        userRepository.save(user);

        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(
                jwtToken,
                refreshToken
        );

        authenticationResponse.setToken(jwtToken);
        authenticationResponse.setRefreshToken(refreshToken);

        return authenticationResponse;
    }


    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return createAndPersistRefreshTokenForDevice(authentication, authenticationRequest)
                .map(RefreshTokenEntity::getToken)
                .map(refreshToken -> {
                    String jwtToken = jwtUtils.generateToken(user);
                    String refreshToken2 = jwtUtils.generateToken(user);

                    AuthenticationResponse authenticationResponse1 = new AuthenticationResponse(jwtToken, refreshToken2);

                    authenticationResponse1.setToken(jwtToken);
                    authenticationResponse1.setRefreshToken(refreshToken2);

                    return authenticationResponse1;
                }).orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: " +
                        "[" + authenticationRequest + "]"));
    }


    public Optional<RefreshTokenEntity> createAndPersistRefreshTokenForDevice(Authentication authentication,
                                                                        AuthenticationRequest loginRequest) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        String deviceId = loginRequest.getDeviceInfo().getDeviceId();
        userDeviceService.findDeviceByUserId(currentUser.getId(), deviceId)
                .map(UserDeviceEntity::getRefreshToken)
                .map(RefreshTokenEntity::getId)
                .ifPresent(refreshTokenService::deleteById);

        UserDeviceEntity userDevice = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(currentUser);
        userDevice.setUser(currentUser);
        userDevice.setRefreshToken(refreshToken);
        refreshToken.setUserDevice(userDevice);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return Optional.ofNullable(refreshToken);
    }


    public String changePassword(PasswordRequest passwordRequest, int id) {

        UserEntity user = userRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Not found user!"));

        user.setPassword(passwordRequest.getPassword());

        userRepository.save(user);

        return "Change Password";
    }
}