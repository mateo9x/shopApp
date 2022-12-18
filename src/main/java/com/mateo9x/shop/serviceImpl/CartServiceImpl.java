package com.mateo9x.shop.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Cart;
import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.CartDTO;
import com.mateo9x.shop.dto.CartUpdateRequestDto;
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
    private final PhotoServiceImpl photoService;

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
    public boolean updateItemAmountInCart(CartUpdateRequestDto cartUpdateRequestDto) {
        log.info("Request to update Item: {} amount in Cart", cartUpdateRequestDto.getItemId());
        Cart cart = cartRepository.findByItemId(cartUpdateRequestDto.getItemId()).orElse(null);
        if (cart == null) {
            return false;
        }
        if (cartUpdateRequestDto.getAmountAvailableAfterBuy().equals(0)) {
            cartRepository.deleteById(cart.getId());
        }
        if (cartUpdateRequestDto.getAmountAvailableAfterBuy() > 0 && cart.getAmountSelected() > cartUpdateRequestDto.getAmountAvailableAfterBuy()) {
            cart.setAmountSelected(cartUpdateRequestDto.getAmountAvailableAfterBuy());
            cartRepository.save(cart);
        }
        return true;
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.info("Request to save Cart: {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long id, Integer amountSelected) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to save Item to User's cart: {}", user.get().getId());
            CartDTO cartDTO = new CartDTO();
            Optional<Cart> isInCartItem = cartRepository.findByItemId(id);
            if (!isInCartItem.isPresent()) {
                cartDTO.setItemId(id);
                cartDTO.setUserId(user.get().getId());
                cartDTO.setAmountSelected(amountSelected);
                Cart cart = cartMapper.toEntity(cartDTO);
                cart = cartRepository.save(cart);
                return cartMapper.toDTO(cart);
            }
            return null;
        }
        return null;
    }

    @Override
    public List<CartDTO> findCartItemsByUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getPrincipal().toString()).orElse(null);
        if (user == null) {
            return null;
        }
        log.info("Request to find Cart for User: {}", user.getId());
        List<CartDTO> cartDTOItems = cartRepository.findByUserId(user.getId()).stream().map(cartMapper::toDTO).collect(Collectors.toList());
        cartDTOItems.forEach(this::fillPhotoForCartDTO);
        return cartDTOItems;
    }

    private void fillPhotoForCartDTO(CartDTO cartDTO) {
        if (!cartDTO.getItemPhotoUrl().equals("-")) {
            if (cartDTO.getItemPhotoUrl().contains(";")) {
                String firstPhotoFileName = cartDTO.getItemPhotoUrl().split(";")[0];
                cartDTO.setItemPhotoFile((photoService.getPhotoFromResourceFolder(cartDTO.getItemSellerId().toString(), firstPhotoFileName)));
            } else {
                cartDTO.setItemPhotoFile(photoService.getPhotoFromResourceFolder(cartDTO.getItemSellerId().toString(), cartDTO.getItemPhotoUrl()));
            }
        }
    }
}
