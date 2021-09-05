package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.ItemDTO;

public interface ItemService {

    ItemDTO save(ItemDTO itemDTO);
    List<ItemDTO> findAll();
    ItemDTO findById(Long id);
    void deleteItem(Long id);
    
}
