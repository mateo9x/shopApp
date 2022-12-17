package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.service.OrderService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.domain.Order;
import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.OrderDTO;
import com.mateo9x.shop.mapper.OrderMapper;
import com.mateo9x.shop.repository.ItemRepository;
import com.mateo9x.shop.repository.OrderRepository;
import com.mateo9x.shop.repository.UserRepository;

import com.mateo9x.shop.service.SellerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private final SellerService sellerService;

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.info("Request to save Order: {}", orderDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userRepository.findByUsername(auth.getPrincipal().toString());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Item item = itemRepository.findById(orderDTO.getItemId()).orElse(null);
            if (item == null) {
                return null;
            }
            item.setAmountAvailable(item.getAmountAvailable() - orderDTO.getAmountBought());
            itemRepository.save(item);
            orderDTO.setUserId(user.getId());
            Order order = orderMapper.toEntity(orderDTO);
            order = orderRepository.save(order);
            order.setDate(LocalDateTime.now());
            if (orderDTO.getOrderPaymentId() != null) {
                sellerService.notifySellerAboutItemBuy(orderDTO);
            }
            return orderMapper.toDTO(order);
        }
        return null;
    }

    @Override
    public List<OrderDTO> findAll() {
        log.info("Request to find all Orders: ");
        return orderRepository.findAll().stream().map(orderMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<OrderDTO> findAllByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to find all Orders by user: {} ", user.get().getId());
            return orderRepository.findByUserId(user.get().getId()).stream().map(orderMapper::toDTO)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        return null;
    }

    @Override
    public OrderDTO findById(Long id) {
        log.info("Request to find Order by id: {}", id);
        Order order = orderRepository.getById(id);
        return orderMapper.toDTO(order);
    }

    @Override
    public void returnProduct(Long orderId, Integer amountOfProductsToReturn) {
        log.info("Request to return Product: {}", orderId);
        Order order = orderRepository.getById(orderId);
        Item item = itemRepository.getById(order.getItem().getId());
        item.setAmountAvailable(item.getAmountAvailable() + amountOfProductsToReturn);
        itemRepository.save(item);
        orderRepository.delete(order);
    }
}
