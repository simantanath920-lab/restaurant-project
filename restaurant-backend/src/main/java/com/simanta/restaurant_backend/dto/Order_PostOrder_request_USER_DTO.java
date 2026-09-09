package com.simanta.restaurant_backend.dto;

import com.simanta.restaurant_backend.model.PaymentMethod;

public class Order_PostOrder_request_USER_DTO {

    private Long addressId;

    private PaymentMethod paymentMethod;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
