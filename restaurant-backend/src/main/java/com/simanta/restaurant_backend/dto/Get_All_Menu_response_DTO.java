package com.simanta.restaurant_backend.dto;

public class Get_All_Menu_response_DTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    private Boolean isAvailable;

    private Boolean isVeg;

    private Integer preprationtime;

    private Integer stock;

    private Long category_id;

    private String categoryname;


    public Get_All_Menu_response_DTO(Long id,String name,String description,Double price,String imageUrl,Boolean isAvailable,Boolean isVeg,
                Integer preprationtime,Integer stock,Long category_id,String categoryname) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
        this.isVeg = isVeg;
        this.preprationtime = preprationtime;
        this.stock = stock;
        this.category_id = category_id;
        this.categoryname = categoryname;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Boolean getIsAvailable() {
        return isAvailable;
    }


    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


    public Boolean getIsVeg() {
        return isVeg;
    }


    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }


    public Integer getPreprationtime() {
        return preprationtime;
    }


    public void setPreprationtime(Integer preprationtime) {
        this.preprationtime = preprationtime;
    }


    public Integer getStock() {
        return stock;
    }


    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public Long getCategory_id() {
        return category_id;
    }


    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }


    public String getCategoryname() {
        return categoryname;
    }


    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
