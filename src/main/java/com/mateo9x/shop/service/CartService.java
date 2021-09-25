package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.CartDTO;

public interface CartService {

    CartDTO save(CartDTO cartDTO);

    CartDTO addItemToCart(Long id);

    List<CartDTO> findAll();

    List<CartDTO> findByUserId(Long id);

    void deleteCart(Long id);

}
