package com.mateo9x.shop.dto;

import java.io.Serializable;

public class ItemCategoryDTO implements Serializable {

    private Long id;
    private String name;
    private Long itemCategoryParentId;
    private String itemCategoryParentName;

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

    public Long getItemCategoryParentId() {
        return itemCategoryParentId;
    }

    public void setItemCategoryParentId(Long itemCategoryParentId) {
        this.itemCategoryParentId = itemCategoryParentId;
    }

    public String getItemCategoryParentName() {
        return itemCategoryParentName;
    }

    public void setItemCategoryParentName(String itemCategoryParentName) {
        this.itemCategoryParentName = itemCategoryParentName;
    }

}
