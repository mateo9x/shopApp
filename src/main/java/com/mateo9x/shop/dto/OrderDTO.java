package com.mateo9x.shop.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable {

    private Long id;
    private String name;
    private Date date;
    private Long userId;
    private String username;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return "OrderDTO [date=" + date + ", id=" + id + ", name=" + name + ", userId=" + userId + ", userame="
                + username + "]";
    }

}
