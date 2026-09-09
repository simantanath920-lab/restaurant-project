package com.simanta.restaurant_backend.exception;

public class OtpExpiredException extends RuntimeException{

    public OtpExpiredException(String message) {
        super(message);
    }
}
