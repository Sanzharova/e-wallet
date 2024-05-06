package com.example.ecomerseshop.controller;


import com.example.ecomerseshop.dto.AuthenticationRequest;
import com.example.ecomerseshop.dto.AuthenticationResponse;
import com.example.ecomerseshop.dto.PasswordRequest;
import com.example.ecomerseshop.dto.RegistrationRequest;
import com.example.ecomerseshop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<AuthenticationResponse> register(RegistrationRequest
                                                                   registerRequest) {
        return ResponseEntity.status(201).body(authenticationService.registrationUser(registerRequest));
    }


    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(201).body(authenticationService.authentication(authenticationRequest));
    }


    public ResponseEntity<String> changePassword(PasswordRequest passwordRequest, Integer id) {

        return ResponseEntity.status(201).body(authenticationService.changePassword(passwordRequest, id));
    }

    public ResponseEntity<String> createGoogleUser() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        String bearerToken = jwtAuthenticationToken.getToken().getTokenValue();

        return ResponseEntity.ok(authenticationService.handleGoogleSignIn(bearerToken));
    }
}