package com.seafood.shop.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    private final ManagementService managementService;

    @Autowired
    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/shop/{shopId}/add-assistant")
    public ResponseEntity<?> addAssistant(
            @PathVariable UUID shopId,
            @RequestBody UUID assistantId,
            @RequestParam UUID ownerId
    ) {
        try {
            managementService.addAssistantToShop(shopId, assistantId, ownerId);
            return ResponseEntity.ok("Assistant added successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}