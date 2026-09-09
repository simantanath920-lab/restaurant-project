package com.simanta.restaurant_backend.dto;

import com.simanta.restaurant_backend.model.PaymentStatus;

public class Payment_Status_COD_request_ADMIN_DTO {

    private Long id;

    private PaymentStatus paymentStatus;

    public Payment_Status_COD_request_ADMIN_DTO(Long id,PaymentStatus paymentStatus){
        this.id = id;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
