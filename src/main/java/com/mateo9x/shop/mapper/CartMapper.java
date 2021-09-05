package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class})
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "itemId", target = "item")
    Cart toEntity(CartDTO cartDTO);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.brand", target = "itemBrand")
    @Mapping(source = "item.model", target = "itemModel")
    CartDTO toDTO(Cart cart);

    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

}
