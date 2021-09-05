package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.mapper.CartMapper;
import com.mateo9x.shop.repository.CartRepository;
import com.mateo9x.shop.service.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public void deleteCart(Long id) {
        log.info("Request to delete Cart: {}", id);
        Cart cart = cartRepository.getById(id);
        cartRepository.delete(cart);
    }

    @Override
    public List<CartDTO> findAll() {
        log.info("Request to find all Carts: ");
        return cartRepository.findAll().stream().map(cartMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public CartDTO findById(Long id) {
        log.info("Request to find Cart: {}", id);
        Cart cart = cartRepository.getById(id);
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.info("Request to save Cart: {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

}
