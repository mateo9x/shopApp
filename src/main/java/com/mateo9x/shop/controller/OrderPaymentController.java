package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;
import com.mateo9x.shop.dto.OrderPaymentDTO;
import com.mateo9x.shop.service.OrderPaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderPaymentController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final OrderPaymentService orderService;

    public OrderPaymentController(OrderPaymentService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders-payment")
    public OrderPaymentDTO createOrder(@Valid @RequestBody OrderPaymentDTO orderDTO) {
        log.debug("REST request to save OrderPayment: {}", orderDTO);
        return orderService.save(orderDTO);
    }

    @PutMapping("/orders-payment")
    public OrderPaymentDTO updateOrder(@Valid @RequestBody OrderPaymentDTO orderDTO) {
        log.debug("REST request to update OrderPayment: {}", orderDTO);
        return orderService.save(orderDTO);
    }

    @GetMapping("/orders-payment")
    public List<OrderPaymentDTO> getAllOrders() {
        log.debug("REST request to get all OrderPayments");
        return orderService.findAll();
    }

    @GetMapping("/orders-payment/{id}")
    public OrderPaymentDTO getOrder(@PathVariable Long id) {
        log.debug("REST request to get OrderPayment: {}", id);
        return orderService.findById(id);
    }

    @DeleteMapping("/orders-payment/{id}")
    public void deleteOrder(@PathVariable Long id) {
        log.debug("REST request do delete OrderPayment: {}", id);
        orderService.delete(id);
    }
}
