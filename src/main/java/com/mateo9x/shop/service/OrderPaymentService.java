package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.OrderPaymentDTO;

public interface OrderPaymentService {

    OrderPaymentDTO save(OrderPaymentDTO orderDTO);

    List<OrderPaymentDTO> findAll();

    OrderPaymentDTO findById(Long id);

    void delete(Long id);

}
