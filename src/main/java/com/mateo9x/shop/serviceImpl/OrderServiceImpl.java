package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.service.OrderService;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository,
            ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.info("Request to save Order: {}", orderDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            Item item = itemRepository.findById(orderDTO.getItemId()).get();
            item.setSold(true);
            itemRepository.save(item);
            orderDTO.setUserId(user.get().getId());
            Order order = orderMapper.toEntity(orderDTO);
            order = orderRepository.save(order);
            return orderMapper.toDTO(order);
        } else {
            return null;
        }

    }

    @Override
    public List<OrderDTO> findAll() {
        log.info("Request to find all Orders: ");
        return orderRepository.findAll().stream().map(orderMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<OrderDTO> findAllByUserId() {
        log.info("Request to find all Orders by user {}: ");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            return orderRepository.findByUserId(user.get().getId()).stream().map(orderMapper::toDTO)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else {
            return null;
        }
    }

    @Override
    public OrderDTO findById(Long id) {
        log.info("Request to find Order: {}", id);
        Order order = orderRepository.getById(id);
        return orderMapper.toDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        log.info("Request to delete Order: {}", id);
        Order order = orderRepository.getById(id);
        Item item = itemRepository.getById(order.getItem().getId());
        item.setSold(false);
        itemRepository.save(item);
        orderRepository.delete(order);
    }

}
