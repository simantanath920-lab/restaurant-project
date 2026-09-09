package com.simanta.restaurant_backend.dto;

public class ForgotPassword_response_DTO {

    private String message;

    public ForgotPassword_response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
