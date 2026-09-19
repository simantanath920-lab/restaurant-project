package com.simanta.restaurant_backend.dto;

import com.simanta.restaurant_backend.model.PaymentStatus;

public class Razorpay_CreatePayment_response_Service_DTO {

    private Long orderId;

    private double totalamount;

    private PaymentStatus paymentStatus;

    private String razorpayOrderId;

    private String key;

    private long amount;

    public Razorpay_CreatePayment_response_Service_DTO(Long orderId, double totalamount, PaymentStatus paymentStatus, String razorpayOrderId,String key,long amount) {
        this.orderId = orderId;
        this.totalamount = totalamount;
        this.paymentStatus = paymentStatus;
        this.razorpayOrderId = razorpayOrderId;
        this.key = key;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
