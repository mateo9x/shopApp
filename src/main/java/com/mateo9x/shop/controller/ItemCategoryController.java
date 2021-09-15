package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;

import com.mateo9x.shop.dto.ItemCategoryDTO;
import com.mateo9x.shop.service.ItemCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemCategoryController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping("/items-category")
    public ItemCategoryDTO createItemCategory(@Valid @RequestBody ItemCategoryDTO itemDTO) {
        log.debug("REST request to save ItemCategory: {}", itemDTO);
        return itemCategoryService.save(itemDTO);
    }

    @PutMapping("/items-category")
    public ItemCategoryDTO updateItemCategory(@Valid @RequestBody ItemCategoryDTO userDTO) {
        log.debug("REST request to update ItemCategory: {}", userDTO);
        return itemCategoryService.save(userDTO);
    }

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

    @DeleteMapping("/items-category/{id}")
    public void deleteItemCategory(@PathVariable Long id) {
        log.debug("REST request do delete ItemCategory: {}", id);
        itemCategoryService.deleteItemCategory(id);
    }

}
