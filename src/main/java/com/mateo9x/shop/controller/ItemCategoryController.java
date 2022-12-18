package com.mateo9x.shop.controller;

import java.util.List;


import com.mateo9x.shop.dto.ItemCategoryDTO;
import com.mateo9x.shop.service.ItemCategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @GetMapping("/items-category")
    public List<ItemCategoryDTO> getAllItemCategories() {
        log.debug("REST request to get all ItemCategories");
        return itemCategoryService.findAll();
    }

    @GetMapping("/items-category/{id}")
    public ItemCategoryDTO getItemCategory(@PathVariable Long id) {
        log.debug("REST request to get ItemCategory: {}", id);
        return itemCategoryService.findById(id);
    }

}
