package com.seafood.inventory.inventory;


import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.sellercatch.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final CatchService catchService; // Assuming CatchService is the service that contains getInventoryBySellerId()

    @Autowired
    public InventoryController(CatchService catchService) {
        this.catchService = catchService;
    }

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/sellers/{sellerId}")
    public Map<SeafoodType, Float> getAvailableKilosBySellerId(@PathVariable Long sellerId) {
        return catchService.getAvailableKilosBySellerId(sellerId);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<Map<SeafoodType, Float>> getInventoryBySeller(@PathVariable Long sellerId) {
        Map<SeafoodType, Float> inventory = inventoryService.getAvailableKilosBySellerId(sellerId);
        return ResponseEntity.ok(inventory);
    }


   /* @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Map<Long, Float>> getInventoryBySellerId(@PathVariable Long sellerId) {
        Map<Long, Float> inventory = inventoryService.getInventoryBySellerId(sellerId);
        return ResponseEntity.ok(inventory);
    }*/
}
