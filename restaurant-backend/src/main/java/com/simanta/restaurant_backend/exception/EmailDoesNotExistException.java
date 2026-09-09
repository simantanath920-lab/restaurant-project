package com.simanta.restaurant_backend.exception;

public class EmailDoesNotExistException extends RuntimeException{

    public EmailDoesNotExistException(String message) {
        super(message);
    }
}
