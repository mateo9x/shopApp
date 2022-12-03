package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.mapper.CartMapper;
import com.mateo9x.shop.repository.CartRepository;
import com.mateo9x.shop.repository.UserRepository;
import com.mateo9x.shop.service.CartService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;

    @Override
    public void deleteItemFromCart(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to delete Item from Cart for User: {}", user.get().getId());
            cartRepository.deleteItemFromUserCart(user.get().getId(), id);
        } else {
            log.error("Can't delete item from Cart");
        }
    }

    @Override
    public void deleteItemFromAllCarts(Long id) {
        log.info("Request to delete Item {} from all Carts", id);
        cartRepository.deleteItemFromAllCarts(id);
    }

    @Override
    public List<CartDTO> findAll() {
        log.info("Request to find all Carts: ");
        return cartRepository.findAll().stream().map(cartMapper::toDTO).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public CartDTO findById(Long id) {
        log.info("Request to find Cart: {}", id);
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.map(cartMapper::toDTO).orElse(null);
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.info("Request to save Cart: {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to save Item to User's cart: {}", user.get().getId());
            CartDTO cartDTO = new CartDTO();
            Optional<Cart> isInCartItem = cartRepository.findByItemId(id);
            if (!isInCartItem.isPresent()) {
                cartDTO.setItemId(id);
                cartDTO.setUserId(user.get().getId());
                Cart cart = cartMapper.toEntity(cartDTO);
                cart = cartRepository.save(cart);
                return cartMapper.toDTO(cart);
            }
            return null;
        }
        return null;
    }
}
