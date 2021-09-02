package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Order;
import com.mateo9x.shop.dto.OrderDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses= {})
public interface OrderMapper {

    // @Mapping(source="userId", target = "user")
    // @Mapping(source="username", target = "user")
    Order toEntity(OrderDTO orderDTO);

    // @Mapping(source="user.id", target = "userId")
    // @Mapping(source="user.username", target = "username")
    OrderDTO toDTO(Order order);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }

}
