package com.mateo9x.shop.dto;

import java.io.Serializable;

public class CartDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long itemId;
    private String itemBrand;
    private String itemModel;
    private Double itemPrice;

    private Integer amountSelected;

    private String itemPhotoUrl;
    private Integer itemAmountAvailable;
    private byte[] itemPhotoFile;
    private Long itemSellerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmountSelected() {
        return amountSelected;
    }

    public void setAmountSelected(Integer amountSelected) {
        this.amountSelected = amountSelected;
    }

    public String getItemPhotoUrl() {
        return itemPhotoUrl;
    }

    public void setItemPhotoUrl(String itemPhotoUrl) {
        this.itemPhotoUrl = itemPhotoUrl;
    }

    public Integer getItemAmountAvailable() {
        return itemAmountAvailable;
    }

    public void setItemAmountAvailable(Integer itemAmountAvailable) {
        this.itemAmountAvailable = itemAmountAvailable;
    }

    public byte[] getItemPhotoFile() {
        return itemPhotoFile;
    }

    public void setItemPhotoFile(byte[] itemPhotoFile) {
        this.itemPhotoFile = itemPhotoFile;
    }

    public Long getItemSellerId() {
        return itemSellerId;
    }

    public void setItemSellerId(Long itemSellerId) {
        this.itemSellerId = itemSellerId;
    }

    @Override
    public String toString() {
        return "CartDTO [id=" + id + ", itemBrand=" + itemBrand + ", itemId=" + itemId + ", itemModel=" + itemModel
                + ", itemPrice=" + itemPrice + ", userId=" + userId + "]";
    }

}
