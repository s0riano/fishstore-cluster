package com.seafood.buyer.model.controller;

import com.seafood.buyer.model.components.CreateBuyer;
import com.seafood.buyer.model.entity.Buyer;
import com.seafood.buyer.model.service.BuyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.seafood.buyer.model.dto.BuyerDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/buyers")
public class BuyerController {

    private final BuyerService buyerService;
    private final CreateBuyer createBuyer;

    @Autowired
    public BuyerController(BuyerService buyerService, CreateBuyer createBuyer) {
        this.buyerService = buyerService;
        this.createBuyer = createBuyer;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

    @PostMapping("/create")
    public ResponseEntity<Buyer> createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) {
        Buyer buyer = createBuyer.handleBuyerCreation(buyerDTO);
        Buyer createdBuyer = buyerService.createBuyer(buyer);
        return ResponseEntity.ok(createdBuyer);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable UUID userId) {
        Buyer buyer = buyerService.getBuyerById(userId);
        return ResponseEntity.ok(buyer);
    }

}
