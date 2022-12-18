package com.mateo9x.shop.controller;

import com.mateo9x.shop.dto.SellerDTO;
import com.mateo9x.shop.service.SellerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/sellers/user-logged")
    public SellerDTO getSellerIfUserLoggedHasSellerAccount() {
        log.info("REST request to get Seller if user logged has seller account");
        return sellerService.getSellerIfUserLoggedHasSellerAccount();
    }

    @PostMapping("/sellers")
    public SellerDTO createSellerAccount() {
        log.info("REST request to create seller account for user logged");
        return sellerService.createSellerAccount();
    }
}
