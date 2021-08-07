package com.mateo9x.shop.dto;

import java.io.Serializable;

public class CartDTO implements Serializable {

    private Long id;
    private String item;
    private Long userId;
    private String username;

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

    @Override
    public String toString() {
        return "CartDTO [id=" + id + ", item=" + item + ", userId=" + userId + ", username=" + username + "]";
    }

}
