package com.mateo9x.shop.service;

import com.mateo9x.shop.dto.OrderDTO;
import com.mateo9x.shop.dto.SellerDTO;

public interface SellerService {
    SellerDTO getSellerById(Long id);

    void notifySellerAboutItemBuy(OrderDTO orderDTO);
}
