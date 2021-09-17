package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.ItemCategoryDTO;

public interface ItemCategoryService {

    ItemCategoryDTO save(ItemCategoryDTO itemCategoryDTO);

    List<ItemCategoryDTO> findAll();
    
    ItemCategoryDTO findById(Long id);

    void deleteItemCategory(Long id);

}
