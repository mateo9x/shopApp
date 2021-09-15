package com.mateo9x.shop.controller;

import java.util.List;

import javax.validation.Valid;

import com.mateo9x.shop.dto.ItemDTO;
import com.mateo9x.shop.service.ItemService;

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
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/item")
    public ItemDTO createItem(@Valid @RequestBody ItemDTO itemDTO) {
        log.debug("REST request to save Item: {}", itemDTO);
        return itemService.save(itemDTO);
    }

    @PutMapping("/item")
    public ItemDTO updateItem(@Valid @RequestBody ItemDTO userDTO) {
        log.debug("REST request to update Item: {}", userDTO);
        return itemService.save(userDTO);
    }

    @GetMapping("/item")
    public List<ItemDTO> getAllItems() {
        log.debug("REST request to get all Item");
        return itemService.findAll();
    }

    @GetMapping("/item/{id}")
    public ItemDTO getItem(@PathVariable Long id) {
        log.debug("REST request to get Item: {}", id);
        return itemService.findById(id);
    }

    @DeleteMapping("/item/{id}")
    public void deleteItem(@PathVariable Long id) {
        log.debug("REST request do delete Item: {}", id);
        itemService.deleteItem(id);
    }

}
