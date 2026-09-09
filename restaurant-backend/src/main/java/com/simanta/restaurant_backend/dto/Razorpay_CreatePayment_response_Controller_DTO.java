package com.simanta.restaurant_backend.dto;

public class Razorpay_CreatePayment_response_Controller_DTO {

    private Long amount;

    private String razorpayOrderId;

    private String key;

    public Razorpay_CreatePayment_response_Controller_DTO(Long amount, String razorpayOrderId, String key) {
        this.amount = amount;
        this.razorpayOrderId = razorpayOrderId;
        this.key = key;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }
}
