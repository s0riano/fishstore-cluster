package com.seafood.shop.controller;

import com.seafood.shop.dto.ShopLandingPageDTO;
import com.seafood.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shops/active")
public class ActiveShopController {

    private final ShopService shopService;

    @Autowired
    public ActiveShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/all")//displaying the shops
    public ResponseEntity<List<ShopLandingPageDTO>> getAllActiveShops() {
        return ResponseEntity.ok(shopService.getAllActiveShops());
    }

    @GetMapping("/all/ids")
    public ResponseEntity<List<UUID>> getAllActiveShopIds() { //displaying all the shops that exist
        return ResponseEntity.ok(shopService.getAllActiveShopIds());
    }

}
