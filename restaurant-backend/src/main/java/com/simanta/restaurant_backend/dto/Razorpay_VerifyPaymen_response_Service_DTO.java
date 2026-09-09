package com.simanta.restaurant_backend.dto;

public class Razorpay_VerifyPaymen_response_Service_DTO {

    private String message;

    public Razorpay_VerifyPaymen_response_Service_DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
