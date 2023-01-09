package com.mateo9x.shop.controller;

import java.util.List;
import javax.validation.Valid;
import com.mateo9x.shop.dto.OrderDTO;
import com.mateo9x.shop.service.OrderService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("REST request to save Order: {}", orderDTO);
        return orderService.save(orderDTO);
    }

    @PutMapping("/orders")
    public OrderDTO finishOrderProcess(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("REST request to finish process of Order: {}", orderDTO);
        return orderService.finishOrderProcess(orderDTO);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        log.debug("REST request to get all Orders");
        return orderService.findAll();
    }

    @GetMapping("/orders/user/{id}")
    public List<OrderDTO> getAllOrdersForUser(@PathVariable Long id) {
        log.debug("REST request to get all Orders for user: {}", id);
        return orderService.findAllByUserId();
    }

    @GetMapping("/orders/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        log.debug("REST request to get Order by id: {}", id);
        return orderService.findById(id);
    }

    @DeleteMapping("/orders/return")
    public void returnOrder(@RequestParam Long id, @RequestParam Integer amountOfProductsToReturn) {
        log.debug("REST request do delete Order: {}", id);
        orderService.returnProduct(id, amountOfProductsToReturn);
    }

    @GetMapping("/orders/seller/{id}")
    public List<OrderDTO> getAllOrdersForSeller(@PathVariable Long id) {
        log.debug("REST request to get all Orders for seller: {}", id);
        return orderService.findAllBySellerId(id);
    }

}
