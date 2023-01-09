package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.OrderDTO;

public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO finishOrderProcess(OrderDTO orderDTO);

    List<OrderDTO> findAll();

    List<OrderDTO> findAllByUserId();

    void returnProduct(Long orderId, Integer amountOfProductsToReturn);

    OrderDTO findById(Long id);

    List<OrderDTO> findAllBySellerId(Long id);

}
