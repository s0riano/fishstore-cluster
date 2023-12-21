package com.seafood.shop.controller;

import com.seafood.shop.component.CreateNewShopComponent;
import com.seafood.shop.dto.CreateShopDTO;
import com.seafood.shop.entity.Shop;
import com.seafood.shop.enums.ShopOwnerRole;
import com.seafood.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;
    private final CreateNewShopComponent createNewShopComponent;


    @Autowired
    public ShopController(CreateNewShopComponent createNewShopComponent, ShopService shopService) {
        this.createNewShopComponent = createNewShopComponent;
        this.shopService = shopService;
    }

    @PostMapping("/{shopId}/addSeller")
    public ResponseEntity<?> addSellerToShop(@PathVariable UUID shopId,
                                             @RequestParam UUID sellerId,
                                             @RequestParam UUID ownerId,
                                             @RequestParam ShopOwnerRole role
    ) {
        try {
            shopService.addSellerToShop(shopId, sellerId, ownerId, role);
            return new ResponseEntity<>("Seller added successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/initialize") //might change these to void?
    public ResponseEntity<Shop> createShop(@RequestParam UUID userId,
                                           @RequestBody CreateShopDTO shopDetails
    ) {
        try {
            Shop createdShop = createNewShopComponent.createShop(userId, shopDetails);
            return ResponseEntity.ok(createdShop);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
