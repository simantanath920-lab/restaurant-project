package com.simanta.restaurant_backend.exception;

public class TokenAlreadyExpired extends RuntimeException{

    public TokenAlreadyExpired(String message) {
        super(message);
    }
}
