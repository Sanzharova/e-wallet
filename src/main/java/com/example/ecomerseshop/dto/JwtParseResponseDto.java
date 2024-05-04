package com.example.ecomerseshop.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtParseResponseDto {

    String username;

    List<String> authorities;

    public JwtParseResponseDto() {
    }

    public JwtParseResponseDto(String username, List<String> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}