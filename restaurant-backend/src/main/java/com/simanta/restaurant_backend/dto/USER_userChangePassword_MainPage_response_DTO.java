package com.simanta.restaurant_backend.dto;

public class USER_userChangePassword_MainPage_response_DTO {

    private String message;

    public USER_userChangePassword_MainPage_response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
