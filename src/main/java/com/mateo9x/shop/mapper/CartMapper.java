package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface CartMapper {

    
    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

}
