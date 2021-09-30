package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.OrderAddressDTO;

public interface OrderAddressService {

    OrderAddressDTO save(OrderAddressDTO orderDTO);

    List<OrderAddressDTO> findAll();

    OrderAddressDTO findById(Long id);

    void delete(Long id);

}
