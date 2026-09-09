package com.simanta.restaurant_backend.exception;

public class EmailAlreadyVerified extends RuntimeException{

    public EmailAlreadyVerified(String message) {
        super(message);
    }
}
