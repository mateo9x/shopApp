package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.dto.CartUpdateRequestDto;

public interface CartService {

    CartDTO save(CartDTO cartDTO);

    CartDTO addItemToCart(Long id, Integer amountSelected);

    void deleteItemFromCart(Long id);

    boolean updateItemAmountInCart(CartUpdateRequestDto cartUpdateRequestDto);

    List<CartDTO> findCartItemsByUserLogged();

}
