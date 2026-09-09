package com.simanta.restaurant_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ForgotPassword_Request_DTO {

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9]+@gmail\\.com$",message = "Enter a valid email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
