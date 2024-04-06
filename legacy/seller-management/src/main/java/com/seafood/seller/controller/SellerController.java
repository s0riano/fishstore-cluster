package com.seafood.seller.controller;

import com.seafood.seller.model.dto.BuyerToSellerDTO;
import com.seafood.seller.model.dto.ValidateSellerDTO;
import com.seafood.seller.model.entity.Seller;
import com.seafood.seller.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @PostMapping("/transfer/{buyerId}") //move this method to a new controller
    public ResponseEntity<Seller> transferBuyerToSeller(@PathVariable UUID buyerId,
                                                        @Valid @RequestBody BuyerToSellerDTO buyerToSellerDTO) {
        try {
            //buyerToSellerDTO.setBuyerId(buyerId);
            Seller seller = sellerService.transferBuyerToSeller(buyerToSellerDTO);
            return ResponseEntity.ok(seller);
        } catch (Exception e) {
            log.error("Error occurred while transferring buyer to seller: {}", e.getMessage());
            throw new RuntimeException("Error in transferring buyer to seller");
        }
    }

    @GetMapping("/validate/{sellerId}")
    public ResponseEntity<ValidateSellerDTO> validateSeller(@PathVariable UUID sellerId) {
        try {
            ValidateSellerDTO validateSellerDTO = sellerService.validateSeller(sellerId);
            return ResponseEntity.ok(validateSellerDTO);
        } catch (Exception e) {
            // Log and handle the exception appropriately
            return ResponseEntity.badRequest().body(null);
        }
    }

    //create endpoint for specific seller on UUID
}