package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.CartDTO;

public interface CartService {

    CartDTO save(CartDTO cartDTO);

    List<CartDTO> findAll();

    CartDTO findById(Long id);

    void deleteCart(Long id);

}
