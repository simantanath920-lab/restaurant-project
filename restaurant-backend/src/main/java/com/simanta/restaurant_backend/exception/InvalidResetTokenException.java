package com.simanta.restaurant_backend.exception;

public class InvalidResetTokenException extends RuntimeException{

    public InvalidResetTokenException(String message) {
        super(message);
    }
}
