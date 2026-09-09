package com.simanta.restaurant_backend.dto;
import java.time.LocalDateTime;

public class Login_response_DTO {

    private String message;

    private LocalDateTime timeStamp;

    private int status;

    private Long userId;

    private String jwtToken;

    private String role;


    public Login_response_DTO(String message,LocalDateTime timeStamp,int status,String jwtToken,String role,Long userId) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.status = status;
        this.jwtToken = jwtToken;
        this.role = role;
        this.userId = userId;
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }   
    
}
