package com.mateo9x.shop.repository;

import java.util.List;
import java.util.Optional;

import com.mateo9x.shop.domain.Cart;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value="select * from carts where user_id = :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);

    @Query(value="select * from carts where item_id = :itemId")
    Optional<Cart> findByItemId(@Param("itemId") Long itemId);
    
}
