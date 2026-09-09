package com.simanta.restaurant_backend.exception;

public class EmailSendingFailedException extends RuntimeException{

    public EmailSendingFailedException(String message) {
        super(message);
    }
}
