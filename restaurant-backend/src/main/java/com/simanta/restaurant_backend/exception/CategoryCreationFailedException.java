package com.simanta.restaurant_backend.exception;

public class CategoryCreationFailedException extends RuntimeException{

    public CategoryCreationFailedException(String message) {
        super(message);
    }
}
