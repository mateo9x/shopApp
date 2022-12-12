package com.mateo9x.shop.controller;

import com.mateo9x.shop.dto.SellerDTO;
import com.mateo9x.shop.service.SellerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/sellers/{id}")
    public SellerDTO getSellerById(@PathVariable Long id) {
        log.info("REST request to get Seller by id: {}", id);
        return sellerService.getSellerById(id);
    }
}
