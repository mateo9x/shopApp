package com.mateo9x.shop.repository;

import com.mateo9x.shop.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(value = "select * from sellers s join items i on i.seller_id = s.id where i.id = :itemId limit 1", nativeQuery = true)
    Seller findSellerByItemId(@Param("itemId") Long itemId);

    Optional<Seller> findSellerByMail(String mail);
}
