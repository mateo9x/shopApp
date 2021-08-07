package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Order;
import com.mateo9x.shop.dto.OrderDTO;

import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    Order toOrder(OrderDTO orderDTO);

    OrderDTO toDTO(Order order);

}
