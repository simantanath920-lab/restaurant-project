package com.simanta.restaurant_backend.exception;

public class RegistrationFailedException extends RuntimeException{

    public RegistrationFailedException(String message) {
        super(message);
    }
}
