package com.example.ecomerseshop.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    String username;

    String password;

    DeviceInfo deviceInfo;

    public AuthenticationRequest() {

    }
}
