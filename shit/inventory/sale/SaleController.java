package com.seafood.inventory.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // Endpoint to get all sales
    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<SaleDTO> saleDTOs = saleService.findAllSales();
        return ResponseEntity.ok(saleDTOs);
    }

    // Endpoint to get sales by catch ID
    @GetMapping("/catch/{catchId}")
    public ResponseEntity<List<SaleDTO>> getSalesByCatchId(@PathVariable Long catchId) {
        List<SaleDTO> sales = saleService.getSalesByCatchId(catchId);
        return ResponseEntity.ok(sales);
    }

    // Endpoint to get all sales for a specific seller
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<SaleDTO>> getSalesBySeller(@PathVariable Long sellerId) {
        List<SaleDTO> salesForSeller = saleService.getSalesBySellerId(sellerId);
        return ResponseEntity.ok(salesForSeller);
    }
}

