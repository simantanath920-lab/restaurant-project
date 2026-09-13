package com.simanta.restaurant_backend.dto;
import java.time.LocalDateTime;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;

public class ADMIN_Orders_Panel_DTO {

    private Long orderid;

    private double totalprice;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private LocalDateTime orderCreatedAt;

    private Long userid;

    private Long addressid;

    public ADMIN_Orders_Panel_DTO(Long orderid,double totalprice ,PaymentMethod paymentMethod ,PaymentStatus paymentStatus ,OrderStatus orderStatus ,LocalDateTime orderCreatedAt ,
         Long userid ,Long addressid){

            this.orderid = orderid;
            this.totalprice = totalprice;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.orderStatus = orderStatus;
            this.orderCreatedAt = orderCreatedAt;
            this.userid = userid;
            this.addressid = addressid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
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

    public LocalDateTime getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(LocalDateTime orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getAddressid() {
        return addressid;
    }

    public void setAddressid(Long addressid) {
        this.addressid = addressid;
    }

}
