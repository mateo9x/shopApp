package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.OrderAddress;
import com.mateo9x.shop.dto.OrderAddressDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { OrderMapper.class, UserMapper.class })
public interface OrderAddressMapper {

    OrderAddressMapper INSTANCE = Mappers.getMapper(OrderAddressMapper.class);

    OrderAddress toEntity(OrderAddressDTO orderAddressDTO);

    OrderAddressDTO toDTO(OrderAddress order);

    default OrderAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderAddress order = new OrderAddress();
        order.setId(id);
        return order;
    }

}
