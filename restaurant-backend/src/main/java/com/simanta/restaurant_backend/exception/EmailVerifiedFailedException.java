package com.simanta.restaurant_backend.exception;

public class EmailVerifiedFailedException extends RuntimeException{

    public EmailVerifiedFailedException(String message) {
        super(message);
    }
}
