package com.seafood.inventory.inventory;


import com.seafood.inventory.entities.dto.CompactInventoryDTO;
import com.seafood.inventory.entities.dto.InventoryDTO;
import com.seafood.inventory.inventory.components.CompactInventoryComponent;
import com.seafood.inventory.sellercatch.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {


    private final CatchService catchService;
    private final InventoryService inventoryService;
    private final CompactInventoryComponent compactInventoryComponent;

    @Autowired
    public InventoryController(CatchService catchService, InventoryService inventoryService, CompactInventoryComponent compactInventoryComponent) {
        this.catchService = catchService;
        this.inventoryService = inventoryService;
        this.compactInventoryComponent = compactInventoryComponent;
    }

    @GetMapping("/seller/{sellerId}") //obsolete?
    public ResponseEntity<InventoryDTO> getInventoryBySellerId(@PathVariable UUID sellerId) {
        InventoryDTO inventory = inventoryService.calculateInventoryForSeller(sellerId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/shop/available-inventory/{shopId}") // make so catch id cant be sent it, need to garantee that the id that is passed in is a seller id. so make a function for that that returns a bool
    public ResponseEntity<InventoryDTO> getAvailableInventory(@PathVariable UUID shopId) {
        InventoryDTO availableInventory = inventoryService.availableInventoryForShop(shopId);
        return ResponseEntity.ok(availableInventory);
    }

    @GetMapping("/shop/compact/{shopId}") // compact overview of a seller
    public ResponseEntity<CompactInventoryDTO> getCompactInventory(@PathVariable UUID shopId) {
        CompactInventoryDTO compactInventory = inventoryService.getCompactInventoryForShop(shopId);
        return ResponseEntity.ok(compactInventory);
    }

    @GetMapping("/shop/compact/all")
    public ResponseEntity<List<CompactInventoryDTO>> getAllShopsInventory() {
        List<CompactInventoryDTO> inventories = compactInventoryComponent.getAllShopsCompactInventory();
        return ResponseEntity.ok(inventories);
    }

}
