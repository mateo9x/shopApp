package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.mateo9x.shop.domain.OrderAddress;
import com.mateo9x.shop.dto.OrderAddressDTO;
import com.mateo9x.shop.mapper.OrderAddressMapper;
import com.mateo9x.shop.repository.OrderAddressRepository;
import com.mateo9x.shop.service.OrderAddressService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    private Logger log = LoggerFactory.getLogger(OrderAddressServiceImpl.class);

    private final OrderAddressRepository orderAddressRepository;
    private final OrderAddressMapper orderAddressMapper;

    public OrderAddressServiceImpl(OrderAddressRepository orderAddressRepository,
            OrderAddressMapper orderAddressMapper) {
        this.orderAddressRepository = orderAddressRepository;
        this.orderAddressMapper = orderAddressMapper;
    }

    @Override
    public OrderAddressDTO save(OrderAddressDTO orderDTO) {
        log.info("Request to save OrderAddress: {}", orderDTO);
        OrderAddress order = orderAddressMapper.toEntity(orderDTO);
        order = orderAddressRepository.save(order);
        return orderAddressMapper.toDTO(order);
    }

    @Override
    public List<OrderAddressDTO> findAll() {
        log.info("Request to find all OrderAddresses: ");
        return orderAddressRepository.findAll().stream().map(orderAddressMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public OrderAddressDTO findById(Long id) {
        log.info("Request to find OrderAddress: {}", id);
        OrderAddress order = orderAddressRepository.getById(id);
        return orderAddressMapper.toDTO(order);
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete OrderAddress: {}", id);
        OrderAddress order = orderAddressRepository.getById(id);
        orderAddressRepository.delete(order);
    }

}
