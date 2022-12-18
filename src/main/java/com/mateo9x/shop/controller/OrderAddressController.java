package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;

import com.mateo9x.shop.dto.OrderAddressDTO;
import com.mateo9x.shop.service.OrderAddressService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderAddressController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final OrderAddressService orderService;

    public OrderAddressController(OrderAddressService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders-address")
    public OrderAddressDTO createOrder(@Valid @RequestBody OrderAddressDTO orderDTO) {
        log.debug("REST request to save OrderAddress: {}", orderDTO);
        return orderService.save(orderDTO);
    }

    @GetMapping("/orders-address")
    public List<OrderAddressDTO> getAllOrders() {
        log.debug("REST request to get all OrderAddresses");
        return orderService.findAll();
    }

    @GetMapping("/orders-address/{id}")
    public OrderAddressDTO getOrder(@PathVariable Long id) {
        log.debug("REST request to get OrderAddress: {}", id);
        return orderService.findById(id);
    }

    @DeleteMapping("/orders-address/{id}")
    public void deleteOrder(@PathVariable Long id) {
        log.debug("REST request do delete OrderAddress: {}", id);
        orderService.delete(id);
    }
}
