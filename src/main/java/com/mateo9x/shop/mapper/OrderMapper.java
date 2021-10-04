package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Order;
import com.mateo9x.shop.dto.OrderDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ItemMapper.class, OrderAddressMapper.class,
        OrderPaymentMapper.class, SellerMapper.class })
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "orderPaymentId", target = "orderPayment")
    @Mapping(source = "orderAddressId", target = "orderAddress")
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "item.brand", target = "itemBrand")
    @Mapping(source = "item.model", target = "itemModel")
    @Mapping(source = "item.price", target = "itemPrice")
    @Mapping(source = "item.seller.name", target = "sellerName")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "orderPayment.id", target = "orderPaymentId")
    @Mapping(source = "orderPayment.type", target = "orderPaymentType")
    @Mapping(source = "orderAddress.id", target = "orderAddressId")
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
