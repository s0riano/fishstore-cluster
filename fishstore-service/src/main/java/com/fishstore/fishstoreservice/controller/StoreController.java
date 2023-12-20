package com.fishstore.fishstoreservice.controller;

import com.fishstore.fishstoreservice.model.entity.Store;
import com.fishstore.fishstoreservice.service.StoreService;
import com.fishstore.fishstoreservice.serviceclients.SellerServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private StoreService storeService;
    private SellerServiceClient sellerServiceClient;

    @Autowired
    public StoreController(StoreService storeService, SellerServiceClient sellerServiceClient) {
        this.storeService = storeService;
        this.sellerServiceClient = sellerServiceClient;
    }

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
