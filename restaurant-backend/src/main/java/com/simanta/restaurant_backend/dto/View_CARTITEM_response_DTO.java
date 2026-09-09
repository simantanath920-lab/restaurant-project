package com.simanta.restaurant_backend.dto;

public class View_CARTITEM_response_DTO {

    private Long menuId;

    private String menuName;

    private Integer quantity;

    private String imageUrl;

    private Double menuPrice;

    private Double subTotal;


    public View_CARTITEM_response_DTO(Long menuId,String menuName,Integer quantity,String imageUrl,Double menuPrice,Double subTotal) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.menuPrice = menuPrice;
        this.subTotal = subTotal;
    }


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
    
}
