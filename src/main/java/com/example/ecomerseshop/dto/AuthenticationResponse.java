package com.example.ecomerseshop.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    String token;

    String refreshToken;

    public AuthenticationResponse(String jwtToken, String refreshToken) {
    }
}