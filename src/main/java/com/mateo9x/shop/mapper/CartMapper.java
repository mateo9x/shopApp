package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class})
public interface CartMapper {

    // @Mapping(source = "itemCategoryId", target = "itemCategory")
    Cart toEntity(CartDTO cartDTO);

    // @Mapping(source = "itemCategory.id", target = "itemCategoryId")
    // @Mapping(source = "itemCategory.name", target = "itemCategoryName")
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
