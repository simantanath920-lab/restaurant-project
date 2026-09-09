package com.simanta.restaurant_backend.exception;

public class CategoryIdNotFoundException extends RuntimeException{

    public CategoryIdNotFoundException(String message) {
        super(message);
    }
}
