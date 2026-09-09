package com.simanta.restaurant_backend.exception;

public class CategoryFetchFailedException extends RuntimeException{

    public CategoryFetchFailedException(String message) {
        super(message);
    }
}
