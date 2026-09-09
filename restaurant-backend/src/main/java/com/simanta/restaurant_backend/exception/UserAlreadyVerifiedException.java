package com.simanta.restaurant_backend.exception;

public class UserAlreadyVerifiedException extends RuntimeException{

    public UserAlreadyVerifiedException(String message) {
        super(message);
    }
}
