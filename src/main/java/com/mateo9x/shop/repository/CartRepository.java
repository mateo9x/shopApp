package com.mateo9x.shop.repository;

import java.util.Optional;

import com.mateo9x.shop.domain.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value="select * from carts where item_id = :itemId", nativeQuery = true)
    Optional<Cart> findByItemId(@Param("itemId") Long itemId);

    @Modifying
    @Transactional
    @Query(value="delete from carts where user_id = :userId and item_id = :itemId", nativeQuery = true)
    void deleteItemFromUserCart(@Param("userId") Long userId, @Param("itemId") Long itemId);
    
}
