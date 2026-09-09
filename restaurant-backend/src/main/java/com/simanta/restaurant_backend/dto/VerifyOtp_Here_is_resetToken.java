package com.simanta.restaurant_backend.dto;

public class VerifyOtp_Here_is_resetToken {

    private String resetToken;

    public VerifyOtp_Here_is_resetToken(String resetToken){
        this.resetToken = resetToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
