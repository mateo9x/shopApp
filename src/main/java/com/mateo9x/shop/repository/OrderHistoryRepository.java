package com.mateo9x.shop.repository;

import com.mateo9x.shop.domain.OrderHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    
}
