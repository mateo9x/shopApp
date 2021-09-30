package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.mateo9x.shop.domain.OrderPayment;
import com.mateo9x.shop.dto.OrderPaymentDTO;
import com.mateo9x.shop.mapper.OrderPaymentMapper;
import com.mateo9x.shop.repository.OrderPaymentRepository;
import com.mateo9x.shop.service.OrderPaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

    private Logger log = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);

    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderPaymentMapper orderPaymentMapper;

    public OrderPaymentServiceImpl(OrderPaymentRepository orderPaymentRepository,
            OrderPaymentMapper orderPaymentMapper) {
        this.orderPaymentRepository = orderPaymentRepository;
        this.orderPaymentMapper = orderPaymentMapper;
    }

    @Override
    public OrderPaymentDTO save(OrderPaymentDTO orderDTO) {
        log.info("Request to save OrderPayment: {}", orderDTO);
        OrderPayment order = orderPaymentMapper.toEntity(orderDTO);
        order = orderPaymentRepository.save(order);
        return orderPaymentMapper.toDTO(order);
    }

    @Override
    public List<OrderPaymentDTO> findAll() {
        log.info("Request to find all OrderPayments: ");
        return orderPaymentRepository.findAll().stream().map(orderPaymentMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public OrderPaymentDTO findById(Long id) {
        log.info("Request to find OrderPayment: {}", id);
        OrderPayment order = orderPaymentRepository.getById(id);
        return orderPaymentMapper.toDTO(order);
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete OrderPayment: {}", id);
        OrderPayment order = orderPaymentRepository.getById(id);
        orderPaymentRepository.delete(order);
    }

}
