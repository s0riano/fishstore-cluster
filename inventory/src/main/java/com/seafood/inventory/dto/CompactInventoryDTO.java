package com.seafood.inventory.dto;

import com.seafood.inventory.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompactInventoryDTO {
    private UUID shopId;
    private List<SeafoodTypeSummaryCompact> seafoodTypeSummaries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeafoodTypeSummaryCompact {
        private SeafoodType seafoodType;
        private BigDecimal totalAvailableKilos; // Total kilos available for this seafood type
        private List<CatchSummary> catchSummaries; // Summarized catches of this seafood type
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatchSummary {
        private UUID catchId;
        private BigDecimal remainingKilos; // Remaining kilos for this catch
    }
}
