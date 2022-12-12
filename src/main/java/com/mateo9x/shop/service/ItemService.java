package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.ItemDTO;

public interface ItemService {

    ItemDTO save(ItemDTO itemDTO);

    List<ItemDTO> findAll();

    List<ItemDTO> findAllFromCategory(Long id);

    List<ItemDTO> findAllBySellerId(Long id);

    List<ItemDTO> findAllByQuery(String query);

    ItemDTO findById(Long id);

    void deleteItem(Long id);

}
