package com.fishstore.fishstoreservice.controller;

import com.fishstore.fishstoreservice.model.entity.Store;
import com.fishstore.fishstoreservice.service.StoreService;
import com.fishstore.fishstoreservice.serviceclients.SellerServiceClient;
import com.seafood.demo.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired //remove in favor of @RequiredArgsConstructor ??
    private SellerServiceClient sellerServiceClient;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody Store store) {
        boolean isValid = storeService.validateSellerId(store.getSellerId());
        if (isValid) {
            storeService.saveStore(store);
            return ResponseEntity.ok("Store created successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid seller_id");
        }
    }

    @GetMapping("/sellers")
    public String getSellers(){

        return sellerServiceClient.retrieveSellers();
    }
}
