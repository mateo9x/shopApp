package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;

import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.dto.CartUpdateRequestDto;
import com.mateo9x.shop.service.CartService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/item-add")
    public CartDTO addItemToCart(@RequestParam Long id, @RequestParam Integer amountSelected) {
        log.debug("REST request to add Item: {} to cart", id);
        return cartService.addItemToCart(id, amountSelected);
    }

    @PostMapping("/cart")
    public CartDTO createCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to save Cart: {}", cartDTO);
        return cartService.save(cartDTO);
    }

    @PutMapping("/cart")
    public CartDTO updateCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to update Cart: {}", cartDTO);
        return cartService.save(cartDTO);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteItemUserCart(@PathVariable Long id) {
        log.debug("REST request do delete Item from User Cart: {}", id);
        cartService.deleteItemFromCart(id);
    }

    @PutMapping("/cart/update-amount")
    public Boolean updateItemAmountInCart(@RequestBody CartUpdateRequestDto cartUpdateRequestDto) {
        log.debug("REST request to update Item: {} amount in user Cart", cartUpdateRequestDto.getItemId());
        return cartService.updateItemAmountInCart(cartUpdateRequestDto);
    }

    @GetMapping("/cart/user")
    public List<CartDTO> getCartItemsByUserLogged() {
        log.debug("REST request to get Cart for User");
        return cartService.findCartItemsByUserLogged();
    }

}
