package com.simanta.restaurant_backend.exception;

public class MenuIdNotFoundException extends RuntimeException{

    public MenuIdNotFoundException(String message) {
        super(message);
    }
}
