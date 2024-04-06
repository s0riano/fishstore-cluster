package com.seafood.inventory.staticInventory.operations.getSpecificStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/store") // Adjust the URL as per your API design
public class GetInventoryForSpecificStoreController {

    private final InventoryForSpecificStoreHandler handler;

    @Autowired
    public GetInventoryForSpecificStoreController(InventoryForSpecificStoreHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/{shopId}") // Endpoint to get inventory for a specific store
    public ResponseEntity<?> getInventoryForStore(@PathVariable UUID shopId) {
        // Call the handle method of your handler with storeId
        handler.handle(shopId);
        // Customize the response as needed
        return ResponseEntity.ok("Inventory processing for shop " + shopId + " completed");
    }
}