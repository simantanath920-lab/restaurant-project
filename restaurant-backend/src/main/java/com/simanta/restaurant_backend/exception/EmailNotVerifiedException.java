package com.simanta.restaurant_backend.exception;

public class EmailNotVerifiedException extends RuntimeException{

    public EmailNotVerifiedException(String message) {
        super(message);
    }
}
