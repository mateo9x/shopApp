package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;

import org.mapstruct.Mapper;

@Mapper
public interface CartMapper {

    Cart toCart(CartDTO cartDTO);

    CartDTO toDTO(Cart cart);
    
}
