package com.simanta.restaurant_backend.exception;

public class InvalidEmailOrPassword extends RuntimeException{

    public InvalidEmailOrPassword(String message) {
        super(message);
    }
}
