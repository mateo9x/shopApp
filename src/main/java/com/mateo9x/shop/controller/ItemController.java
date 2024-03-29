package com.mateo9x.shop.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.shop.dto.ItemDTO;
import com.mateo9x.shop.service.ItemService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ItemDTO createItem(@RequestPart(name = "item") String itemString, @RequestPart("photos") List<MultipartFile> photos) {
        try {
            ItemDTO item = new ObjectMapper().readValue(itemString, ItemDTO.class);
            log.debug("REST request to save Item: {}", item);
            return itemService.save(item, photos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/item/category/{id}")
    public List<ItemDTO> getAllItemsFromCategory(@PathVariable Long id) {
        log.debug("REST request to get all Item for category: {}", id);
        return itemService.findAllFromCategory(id);
    }

    @GetMapping("/item/seller/active/{id}")
    public List<ItemDTO> getAllItemsBySellerIdActive(@PathVariable Long id) {
        log.debug("REST request to get all Item active for seller: {}", id);
        return itemService.findAllBySellerIdActive(id);
    }

    @GetMapping("/item/query/{query}")
    public List<ItemDTO> getAllItemsByQuery(@PathVariable String query) {
        log.debug("REST request to get all Item by query: {}", query);
        return itemService.findAllByQuery(query);
    }

    @GetMapping("/item/{id}")
    public ItemDTO getItem(@PathVariable Long id) {
        log.debug("REST request to get Item: {}", id);
        return itemService.findById(id);
    }

    @GetMapping("item/exists")
    public Boolean doesItemAlreadyExists(ItemDTO itemDTO) {
        log.debug("REST request to check if Item Already Exists: {}", itemDTO);
        return itemService.doesItemAlreadyExists(itemDTO);
    }

}
