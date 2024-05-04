package com.example.ecomerseshop.exception;


public class TokenRefreshException extends RuntimeException {

    private final String token;
    private final String message;

    public TokenRefreshException(String token, String message) {
        super(String.format("Couldn't refresh token for [%s]: [%s])", token, message));
        this.token = token;
        this.message = message;
    }
}