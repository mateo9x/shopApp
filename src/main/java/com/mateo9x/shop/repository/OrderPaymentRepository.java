package com.mateo9x.shop.repository;

import com.mateo9x.shop.domain.OrderPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {

}