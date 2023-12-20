package com.fishtore.inventory.staticinventory.inventory;

import com.fishtore.inventory.staticinventory.dto.PriceInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory == null) {
            return ResponseEntity.notFound().build(); // or another appropriate error
        }
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/prices/{sellerId}")
    public ResponseEntity<List<PriceInfoDTO>> getPricesForSeller(@PathVariable Long sellerId) {
        List<PriceInfoDTO> prices = inventoryService.findPricesBySeller(sellerId);
        return ResponseEntity.ok(prices);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.createInventory(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory existingInventory = inventoryService.getInventoryById(id);
        if (existingInventory == null) {
            return ResponseEntity.notFound().build();
        }
        inventory.setInventoryId(id); // Ensure the ID is set to the path variable
        return ResponseEntity.ok(inventoryService.updateInventory(inventory));
    }
}
