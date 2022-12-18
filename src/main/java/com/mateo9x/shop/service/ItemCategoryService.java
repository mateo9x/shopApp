package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.ItemCategoryDTO;

public interface ItemCategoryService {

    List<ItemCategoryDTO> findAll();

    ItemCategoryDTO findById(Long id);

}
