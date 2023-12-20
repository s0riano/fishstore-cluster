package com.fishtore.price.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> getAllPrices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getPriceByShopId(@PathVariable UUID shopId) {
        String shopIdAsString = shopId.toString();
        Optional<Price> price = priceService.getPricesById(shopIdAsString);
        log.info("The price list for the seller {} is: {}",shopId, price);
        if (price.isPresent()) {
            return ResponseEntity.ok(price.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No id found in the database");
        }
    }

    //add endpoint for /all shop prices
}
