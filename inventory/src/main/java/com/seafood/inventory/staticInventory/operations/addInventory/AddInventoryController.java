package com.seafood.inventory.staticInventory.operations.addInventory;

import com.seafood.inventory.staticInventory.Inventory;
import com.seafood.inventory.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class AddInventoryController {

    private final AddInventoryHandler addInventoryHandler;

    @Autowired
    public AddInventoryController(AddInventoryHandler addInventoryHandler) {
        this.addInventoryHandler = addInventoryHandler;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInventory(@RequestBody Inventory inventory, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID storeId = userDetails.getShopId();
        Inventory updatedInventory = addInventoryHandler.handle(inventory, storeId);
        return ResponseEntity.ok(updatedInventory);
    }
}