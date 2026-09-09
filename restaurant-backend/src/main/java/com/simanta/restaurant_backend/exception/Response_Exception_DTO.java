package com.simanta.restaurant_backend.exception;

import java.time.LocalDateTime;

public class Response_Exception_DTO {

    private String message;
    private LocalDateTime timeStamp;
    private int status;

    public Response_Exception_DTO(String message,LocalDateTime timeStamp,int status) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.status = status;
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
}
