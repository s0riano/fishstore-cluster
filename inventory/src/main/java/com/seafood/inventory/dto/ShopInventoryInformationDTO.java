package com.seafood.inventory.dto;

import com.seafood.inventory.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInventoryInformationDTO {

    /*
    This file might be important for the seller to see his owns inventory and history etc
    */

    private UUID shopId;
    private List<SeafoodTypeSummary> seafoodTypeSummaries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeafoodTypeSummary {
        private SeafoodType seafoodType;
        private Float totalAvailableKilos; // Total kilos available for this seafood type
        private List<CatchInventory> catchInventories; // Detailed catches of this seafood type
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatchInventory {
        private UUID catchId;
        private SeafoodType seafoodType;
        private Float remainingKilos; // Calculated remaining kilos after sales
        private Float totalKilos; // Total kilos at the time of the catch
        private List<SaleInCatchDTO> sales; // Sales made from this catch
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleInCatchDTO {
        private UUID saleId;
        private Float kilosSold; // Kilos sold in this sale
    }
}
