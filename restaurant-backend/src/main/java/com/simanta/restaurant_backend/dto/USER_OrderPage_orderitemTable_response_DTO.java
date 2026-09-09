package com.simanta.restaurant_backend.dto;

public class USER_OrderPage_orderitemTable_response_DTO {

    private String itemname;

    private Integer quantity;

    private double totalprice;

    public USER_OrderPage_orderitemTable_response_DTO(String itemname,Integer quantity,double totalprice){
        this.itemname = itemname;
        this.quantity = quantity;
        this.totalprice = totalprice;
    }
 
    public String getItemname() {
        return itemname;
    } 

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }
}
