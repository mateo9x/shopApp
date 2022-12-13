package com.mateo9x.shop.dto;

import lombok.Getter;

@Getter
public class CartUpdateRequestDto {

    private Long itemId;
    private Integer amountAvailableAfterBuy;
}
