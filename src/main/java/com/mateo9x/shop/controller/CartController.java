package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;

import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.service.CartService;

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
public class CartController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    public CartDTO createCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to save Cart: {}", cartDTO);
        return cartService.save(cartDTO);
    }

    @PutMapping("/carts")
    public CartDTO updateCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to update Cart: {}", cartDTO);
        return cartService.save(cartDTO);
    }

    @GetMapping("/carts")
    public List<CartDTO> getAllCarts() {
        log.debug("REST request to get all Carts");
        return cartService.findAll();
    }

    @GetMapping("/carts/{id}")
    public CartDTO getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart: {}", id);
        return cartService.findById(id);
    }

    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable Long id) {
        log.debug("REST request do delete Cart: {}", id);
        cartService.deleteCart(id);
    }

}
