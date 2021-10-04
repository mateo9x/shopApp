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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  private final CartRepository cartRepository;
  private final CartMapper cartMapper;
  private final UserRepository userRepository;

  public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper, UserRepository userRepository) {
    this.cartRepository = cartRepository;
    this.cartMapper = cartMapper;
    this.userRepository = userRepository;
  }

  @Override
  public void deleteItemFromCart(Long id) {
    log.info("Request to delete Item from Cart: {}");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
    if (user.isPresent()) {
      cartRepository.deleteItemFromUserCart(user.get().getId(), id);
    } else {
      log.error("Can't delete item from Cart");
    }
  }

  @Override
  public void deleteItemFromAllCarts(Long id) {
    log.info("Request to delete Item from all Carts: {}");
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
    if (cart.isPresent()) {
      CartDTO dto = cartMapper.toDTO(cart.get());
      return dto;
    } else {
      return null;
    }
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
    log.info("Request to save Item to User cart: {}");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
    if (user.isPresent()) {
      CartDTO cartDTO = new CartDTO();
      Optional<Cart> isInCartItem = cartRepository.findByItemId(id);
      if (!isInCartItem.isPresent()) {
        cartDTO.setItemId(id);
        cartDTO.setUserId(user.get().getId());
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

}
