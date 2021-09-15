package com.mateo9x.shop.dto;

import java.io.Serializable;

public class CartDTO implements Serializable {

    private Long id;
    private String item;
    private Long userId;
    private String username;
    private Long itemId;
    private String itemBrand;
    private String itemModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    @Override
    public String toString() {
        return "CartDTO [id=" + id + ", item=" + item + ", itemBrand=" + itemBrand + ", itemId=" + itemId
                + ", itemModel=" + itemModel + ", userId=" + userId + ", username=" + username + "]";
    }

    

}
