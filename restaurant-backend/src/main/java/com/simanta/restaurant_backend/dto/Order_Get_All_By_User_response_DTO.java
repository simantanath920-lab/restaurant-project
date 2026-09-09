package com.simanta.restaurant_backend.dto;
import java.util.ArrayList;
import java.util.List;

import com.simanta.restaurant_backend.model.OrderStatus;

public class Order_Get_All_By_User_response_DTO {

     private Long id;

     private Long user_id;

     private List<OrderItem_Get_All_By_User_response_DTO> orderitems = new ArrayList<>();

     private Long address_id;

     private OrderStatus orderStatus;

     private double totalprice;


    public Order_Get_All_By_User_response_DTO(Long id, Long user_id,List<OrderItem_Get_All_By_User_response_DTO> orderitems, Long address_id, OrderStatus orderStatus, double totalprice) {
        this.id = id;
        this.user_id = user_id;
        this.orderitems = orderitems;
        this.address_id = address_id;
        this.orderStatus = orderStatus;
        this.totalprice = totalprice;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<OrderItem_Get_All_By_User_response_DTO> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<OrderItem_Get_All_By_User_response_DTO> orderitems) {
        this.orderitems = orderitems;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }
}
