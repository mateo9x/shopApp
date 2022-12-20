package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.OrderPayment;
import com.mateo9x.shop.dto.OrderPaymentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderPaymentMapper {

    OrderPaymentMapper INSTANCE = Mappers.getMapper(OrderPaymentMapper.class);

    OrderPayment toEntity(OrderPaymentDTO orderPaymentDTO);

    OrderPaymentDTO toDTO(OrderPayment order);

    default OrderPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderPayment order = new OrderPayment();
        order.setId(id);
        return order;
    }

}
