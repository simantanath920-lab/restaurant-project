package com.simanta.restaurant_backend.exception;

public class ResendVerificationLinkFailedException extends RuntimeException{

    public ResendVerificationLinkFailedException(String message) {
        super(message);
    }
}
