package com.mateo9x.shop.repository;

import java.util.List;

import com.mateo9x.shop.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query(value = "select * from orders where user_id = :userId", nativeQuery = true)
    List<Order> findByUserId(@Param("userId") Long userId);

}
