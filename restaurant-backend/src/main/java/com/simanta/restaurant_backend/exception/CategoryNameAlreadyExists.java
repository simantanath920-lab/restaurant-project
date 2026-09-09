package com.simanta.restaurant_backend.exception;

public class CategoryNameAlreadyExists extends RuntimeException{

    public CategoryNameAlreadyExists(String message) {
        super(message);
    }

}
