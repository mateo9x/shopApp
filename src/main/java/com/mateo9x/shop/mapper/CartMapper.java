package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses= {ItemMapper.class, UserMapper.class})
public interface CartMapper {

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "userId", target = "user")
    Cart toEntity(CartDTO cartDTO);

    @Mapping(source="user.id", target = "userId")
    @Mapping(source="user.username", target = "username")
    @Mapping(source="item.id", target = "itemId")
    @Mapping(source="item.brand", target = "itemBrand")
    @Mapping(source="item.model", target = "itemModel")
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
