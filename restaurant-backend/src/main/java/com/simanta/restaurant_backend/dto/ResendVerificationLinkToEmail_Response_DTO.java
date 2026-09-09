package com.simanta.restaurant_backend.dto;

public class ResendVerificationLinkToEmail_Response_DTO {

    private String message;

    public ResendVerificationLinkToEmail_Response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
