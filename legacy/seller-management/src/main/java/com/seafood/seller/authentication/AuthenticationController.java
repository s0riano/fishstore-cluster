package com.seafood.seller.authentication;

import com.seafood.seller.model.dto.ValidateSellerDTO;
import com.seafood.seller.service.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public class AuthenticationController {

    private final SellerService sellerService;

    public AuthenticationController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/authenticate/{sellerId}")
    public ResponseEntity<ValidateSellerDTO> validateSeller(@PathVariable UUID sellerId) {
        try {
            ValidateSellerDTO validateSellerDTO = sellerService.validateSeller(sellerId);
            return ResponseEntity.ok(validateSellerDTO);
        } catch (Exception e) {
            // Log and handle the exception appropriately
            return ResponseEntity.badRequest().body(null);
        }
    }
}
