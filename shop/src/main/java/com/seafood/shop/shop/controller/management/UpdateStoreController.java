package com.seafood.shop.shop.controller.management;

import com.seafood.shop.config.jwt.JwtService;
import com.seafood.shop.dto.shopDTOs.management.UpdateStoreDescriptionDTO;
import com.seafood.shop.shop.Shop;
import com.seafood.shop.shop.ShopService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public class UpdateStoreController {
    private final ShopService shopService;
    private final JwtService jwtService;

    public UpdateStoreController(ShopService shopService, JwtService jwtService) {
        this.shopService = shopService;
        this.jwtService = jwtService;
    }

    @PutMapping("/api/v1/update/description")
    public ResponseEntity<?> updateStoreDescription(@RequestBody UpdateStoreDescriptionDTO updateRequest,
                                                    HttpServletRequest request) {
        String jwtToken = extractJwtFromRequest(request);
        if (jwtToken == null || !jwtService.isTokenValid(jwtToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or Missing JWT Token");
        }

        UUID storeId = getStoreIdFromJwt(jwtToken);
        if (storeId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Store ID not found in token");
        }

        Shop updatedShop = shopService.updateStoreDescription(storeId, updateRequest.getNewDescription());
        return ResponseEntity.ok(updatedShop);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private UUID getStoreIdFromJwt(String jwtToken) {
        return UUID.fromString(jwtService.extractClaim(jwtToken, claims -> claims.get("store_id", String.class)));
    }
}
