package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.OrderHistory;
import com.mateo9x.shop.dto.OrderHistoryDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface OrderHistoryMapper {

    OrderHistoryMapper INSTANCE = Mappers.getMapper(OrderHistoryMapper.class);

    // @Mapping(source = "orderId", target = "order")
    OrderHistory toEntity(OrderHistoryDTO orderHistoryDTO);

    // @Mapping(source = "order.id", target = "orderId")
    // @Mapping(source = "order.name", target = "orderName")
    OrderHistoryDTO toDTO(OrderHistory order);

    default OrderHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderHistory order = new OrderHistory();
        order.setId(id);
        return order;
    }

}
