package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.OrderDTO;

public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    List<OrderDTO> findAll();

    List<OrderDTO> findAllByUserId();

    OrderDTO findById(Long id);

    void deleteOrder(Long id);

}
