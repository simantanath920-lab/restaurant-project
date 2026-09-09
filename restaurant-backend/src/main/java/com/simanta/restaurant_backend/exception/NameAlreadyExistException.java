package com.simanta.restaurant_backend.exception;

public class NameAlreadyExistException extends RuntimeException{

    public NameAlreadyExistException(String message) {
        super(message);
    }
}
