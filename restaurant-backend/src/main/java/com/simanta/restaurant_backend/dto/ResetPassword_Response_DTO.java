package com.simanta.restaurant_backend.dto;

public class ResetPassword_Response_DTO {

    private String message;

    public ResetPassword_Response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
