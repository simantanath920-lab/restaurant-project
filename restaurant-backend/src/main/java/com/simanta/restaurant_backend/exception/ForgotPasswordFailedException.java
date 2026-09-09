package com.simanta.restaurant_backend.exception;

public class ForgotPasswordFailedException extends RuntimeException{

    public ForgotPasswordFailedException(String message) {
        super(message);
    }
}
