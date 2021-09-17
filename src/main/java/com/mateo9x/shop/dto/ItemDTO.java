package com.mateo9x.shop.dto;

import java.io.Serializable;

public class ItemDTO implements Serializable {

    private Long id;
    private String brand;
    private String model;
    private Double price;
    private Long itemCategoryId;
    private String itemCategoryName;
    private Integer sold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public Integer isSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

}
