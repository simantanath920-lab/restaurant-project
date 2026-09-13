package com.simanta.restaurant_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;

public class USER_OrderPage_CurrentOrder_response_DTO {

    private Long orderid;

    private LocalDateTime createdAt;
 
    private OrderStatus orderStatus;

    private List<USER_OrderPage_orderitemTable_response_DTO> orderitems;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private String city;

    private String area;

    private String street;

    private String phoneNumber;


    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<USER_OrderPage_orderitemTable_response_DTO> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<USER_OrderPage_orderitemTable_response_DTO> orderitems) {
        this.orderitems = orderitems;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
