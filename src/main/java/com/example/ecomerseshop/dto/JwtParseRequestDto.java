package com.example.ecomerseshop.dto;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtParseRequestDto {

    String token;

    public JwtParseRequestDto() {
    }

    public JwtParseRequestDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtParseRequestDto{" +
                "token='" + token + '\'' +
                '}';
    }
}