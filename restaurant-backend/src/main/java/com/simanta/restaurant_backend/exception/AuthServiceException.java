package com.simanta.restaurant_backend.exception;

public class AuthServiceException extends RuntimeException{

    public AuthServiceException(String message){
        super(message);
    }
}
