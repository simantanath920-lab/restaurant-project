package com.simanta.restaurant_backend.exception;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(String message) {
        super(message);
    }
}
