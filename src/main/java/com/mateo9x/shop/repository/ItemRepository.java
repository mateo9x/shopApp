package com.mateo9x.shop.repository;

import java.util.List;
import java.util.Optional;

import com.mateo9x.shop.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByItemCategoryId(Long id);

    @Query(value = "select * from items where seller_id = :sellerId", nativeQuery = true)
    List<Item> findBySellerId(@Param("sellerId") Long sellerId);

    @Query(value = "select * from items where brand ilike %:query% or model ilike %:query%", nativeQuery = true)
    List<Item> findByBrandLikeOrModelLike(@Param("query") String query);

    @Query(value = "select * from items where brand = :brand and model = :model and price = :price and seller_id = :sellerId and items_category_id = :itemsCategoryId and amount_available > 0;", nativeQuery = true)
    Optional<Item> doesItemAlreadyExists(@Param("brand") String brand, @Param("model") String model, @Param("price") Double price, @Param("sellerId") Long sellerId, @Param("itemsCategoryId") Long itemsCategoryId);

}
