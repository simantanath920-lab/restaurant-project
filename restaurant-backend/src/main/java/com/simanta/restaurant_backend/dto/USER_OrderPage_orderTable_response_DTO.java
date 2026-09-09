package com.simanta.restaurant_backend.dto;

import java.util.Date;
import java.util.List;

import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;

public class USER_OrderPage_orderTable_response_DTO {

    private Long orderid;

    private Date createdAt;
 
    private OrderStatus orderStatus;
 
    private List<USER_OrderPage_orderitemTable_response_DTO> orderitems;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private String city;

    private String area;

    private String street;

    private String phoneNumber;
    

    public USER_OrderPage_orderTable_response_DTO(Long orderid,Date createdAt,OrderStatus orderStatus,List<USER_OrderPage_orderitemTable_response_DTO> orderitems,PaymentStatus paymentStatus,
        PaymentMethod paymentMethod,String city,String area,String street,String phoneNumber){
            this.orderid = orderid;
            this.createdAt = createdAt;
            this.orderStatus = orderStatus;
            this.orderitems = orderitems;
            this.paymentStatus = paymentStatus;
            this.paymentMethod = paymentMethod;
            this.city = city;
            this.area = area;
            this.street = street;
            this.phoneNumber = phoneNumber;
    }

    public Long getOrderid() {
        return orderid;
    }


    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }


    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
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
