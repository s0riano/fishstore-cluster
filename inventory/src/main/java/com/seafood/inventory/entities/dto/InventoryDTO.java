package com.seafood.inventory.entities.dto;

import com.seafood.inventory.entities.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    //Had many ideas for how this should be done, many of them are still in the project too
    private UUID shopId;
    private List<CatchInventory> catchInventories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatchInventory {
        private UUID catchId;
        private SeafoodType seafoodType;
        //private String status; // "Available", "Sold Out", etc.
        private BigDecimal remainingKilos; // Calculated remaining kilos after sales
        private BigDecimal totalKilos;
        private List<SaleInCatchDTO> sales;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleInCatchDTO {
        private UUID saleId;
        private BigDecimal kilosSold;
    }
}
