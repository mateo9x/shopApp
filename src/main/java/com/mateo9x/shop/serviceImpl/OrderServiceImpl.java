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
    private final PhotoServiceImpl photoService;

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
            orderDTO.setDate(LocalDateTime.now());
            Order order = orderMapper.toEntity(orderDTO);
            order = orderRepository.save(order);
            return orderMapper.toDTO(order);
        }
        return null;
    }

    @Override
    public OrderDTO finishOrderProcess(OrderDTO orderDTO) {
        return orderMapper.toDTO(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    @Override
    public List<OrderDTO> findAll() {
        log.info("Request to find all Orders: ");
        List<OrderDTO> orderDTOS = orderRepository.findAll().stream().map(orderMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        orderDTOS.forEach(this::fillPhotoForOrderDTO);
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> findAllByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to find all Orders by user: {} ", user.get().getId());
            List<OrderDTO> orderDTOS = orderRepository.findByUserId(user.get().getId()).stream().map(orderMapper::toDTO)
                    .collect(Collectors.toCollection(LinkedList::new));
            orderDTOS.forEach(this::fillPhotoForOrderDTO);
            return orderDTOS;
        }
        return null;
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

    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        return orderMapper.toDTO(order);
    }

    private void fillPhotoForOrderDTO(OrderDTO orderDTO) {
        if (!orderDTO.getPhotoUrl().equals("-")) {
            if (orderDTO.getPhotoUrl().contains(";")) {
                String firstPhotoFileName = orderDTO.getPhotoUrl().split(";")[0];
                orderDTO.setPhoto((photoService.getPhotoFromResourceFolder(orderDTO.getItemId().toString(), firstPhotoFileName)));
            } else {
                orderDTO.setPhoto(photoService.getPhotoFromResourceFolder(orderDTO.getItemId().toString(), orderDTO.getPhotoUrl()));
            }
        }
    }
}
