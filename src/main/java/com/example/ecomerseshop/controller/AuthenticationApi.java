package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.AuthenticationRequest;
import com.example.ecomerseshop.dto.AuthenticationResponse;
import com.example.ecomerseshop.dto.PasswordRequest;
import com.example.ecomerseshop.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthenticationApi {

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registerRequest);

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest);

    @PostMapping("/changePassword/{id}")
    ResponseEntity<String> changePassword(@RequestBody PasswordRequest passwordRequest,
                                          @PathVariable Integer id);

    @PostMapping("/authenticate/google")
    ResponseEntity<String> createGoogleUser();
}
