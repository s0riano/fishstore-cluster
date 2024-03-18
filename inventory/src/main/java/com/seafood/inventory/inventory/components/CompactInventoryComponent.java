package com.seafood.inventory.inventory.components;

import com.seafood.inventory.entities.dto.CompactInventoryDTO;
import com.seafood.inventory.entities.enums.SeafoodType;
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.Catch;
import com.seafood.inventory.sellercatch.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CompactInventoryComponent {

    private final CatchRepository catchRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public CompactInventoryComponent(CatchRepository catchRepository, SaleRepository saleRepository) {
        this.catchRepository = catchRepository;
        this.saleRepository = saleRepository;
    }

    public CompactInventoryDTO getCompactInventoryForShop(UUID shopId) {
        List<Catch> catches = catchRepository.findByShopId(shopId);
        Map<SeafoodType, CompactInventoryDTO.SeafoodTypeSummaryCompact> summaryMap = new HashMap<>();

        for (Catch c : catches) {
            SeafoodType type = c.getSeafoodType();
            BigDecimal totalKilos = c.getKilos();
            BigDecimal soldKilos = saleRepository.sumKilosByShopIdAndSeafoodType(shopId, type);

            CompactInventoryDTO.SeafoodTypeSummaryCompact summary = summaryMap.computeIfAbsent(
                    type, k -> new CompactInventoryDTO.SeafoodTypeSummaryCompact(type, BigDecimal.ZERO, new ArrayList<>()));

            BigDecimal availableKilos = totalKilos.subtract(soldKilos);
            summary.setTotalAvailableKilos(summary.getTotalAvailableKilos().add(availableKilos));
            summary.getCatchSummaries().add(new CompactInventoryDTO.CatchSummary(c.getCatchId(), availableKilos));
        }

        return new CompactInventoryDTO(shopId, new ArrayList<>(summaryMap.values()));
    }

    public List<CompactInventoryDTO> getAllShopsCompactInventory() {
        List<UUID> allShopIds = catchRepository.findAllDistinctShopIds();
        return allShopIds.stream()
                .map(this::getCompactInventoryForShop)
                .filter(dto -> !dto.getSeafoodTypeSummaries().isEmpty())  // Filter out shops with no inventory
                .collect(Collectors.toList());
    }
}
