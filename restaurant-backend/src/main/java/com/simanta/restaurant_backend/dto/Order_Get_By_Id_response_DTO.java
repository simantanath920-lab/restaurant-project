package com.simanta.restaurant_backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.simanta.restaurant_backend.model.OrderStatus;

public class Order_Get_By_Id_response_DTO {

    private Long order_id;

    private Long user_id;

    private List<Orderitem_Get_By_Id_response_DTO> orderitems = new ArrayList<>();

    private Address_View_response_USER_DTO selectdefaultaddress;

    private OrderStatus orderStatus;

    private double totalprice;


    public Order_Get_By_Id_response_DTO(Long order_id,Long user_id,List<Orderitem_Get_By_Id_response_DTO> orderitems,Address_View_response_USER_DTO selectdefaultaddress,OrderStatus orderStatus,double totalprice) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.orderitems = orderitems;
        this.selectdefaultaddress = selectdefaultaddress;
        this.orderStatus = orderStatus;
        this.totalprice = totalprice;
    }

    public Long getOrder_id() {
        return order_id;
    }


    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }


    public Long getUser_id() {
        return user_id;
    }


    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }


    public List<Orderitem_Get_By_Id_response_DTO> getOrderitems() {
        return orderitems;
    }


    public void setOrderitems(List<Orderitem_Get_By_Id_response_DTO> orderitems) {
        this.orderitems = orderitems;
    }


    public Address_View_response_USER_DTO getSelectdefaultaddress() {
        return selectdefaultaddress;
    }


    public void setSelectdefaultaddress(Address_View_response_USER_DTO selectdefaultaddress) {
        this.selectdefaultaddress = selectdefaultaddress;
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
