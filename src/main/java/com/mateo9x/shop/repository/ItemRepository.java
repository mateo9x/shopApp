package com.mateo9x.shop.repository;

import com.mateo9x.shop.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
