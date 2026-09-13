package com.simanta.restaurant_backend.dto;

import java.time.LocalDateTime;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;

public class Order_Table_orderstatus_AND_paymentstatus_response_DTO {

    // order
    private Long orderid;

    // user
    private Long userid;

    private Long paymentid;

    // order
    private double totalprice;

    // payment
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    // order
    private OrderStatus orderStatus;

    // order
    private LocalDateTime createdAt;

    public Order_Table_orderstatus_AND_paymentstatus_response_DTO(Long orderid,Long userid,Long paymentid,double totalprice,PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,OrderStatus orderStatus,LocalDateTime createdAt){

            this.orderid = orderid;
            this.userid = userid;
            this.paymentid = paymentid;
            this.totalprice = totalprice;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.orderStatus = orderStatus;
            this.createdAt = createdAt;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Long paymentid) {
        this.paymentid = paymentid;
    }
}
