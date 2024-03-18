package com.seafood.inventory.components;

import com.seafood.inventory.entities.dto.InventoryDTO;
import com.seafood.inventory.entities.dto.InventoryDTO.CatchInventory;
import com.seafood.inventory.entities.dto.InventoryDTO.SaleInCatchDTO;
import com.seafood.inventory.sellercatch.CatchService;
import com.seafood.inventory.sale.SaleService;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryComponent {

    private final CatchService catchService;
    private final SaleService saleService;

    public InventoryDTO calculateInventoryForUser(UUID userId) {
        List<CatchInventory> catchInventories = catchService.getCatchesBySellerId(userId).stream()
                .map(catchEntity -> {
                    List<SaleInCatchDTO> sales = saleService.getSalesByCatchId(catchEntity.getCatchId())
                            .stream()
                            .map(saleDTO -> new SaleInCatchDTO(saleDTO.getSaleId(), saleDTO.getKilos())) // Using BigDecimal directly
                            .collect(Collectors.toList());
                    BigDecimal remainingKilos = calculateRemainingKilos(catchEntity.getKilos(), sales);

                    return new CatchInventory(
                            catchEntity.getCatchId(),
                            catchEntity.getSeafoodType(),
                            remainingKilos,
                            catchEntity.getKilos(), // Currently for double checking
                            sales
                    );
                })
                .filter(catchInventory -> catchInventory.getRemainingKilos().compareTo(BigDecimal.ZERO) > 0) // Using compareTo for BigDecimal
                .collect(Collectors.toList());

        return new InventoryDTO(userId, catchInventories);
    }

    private BigDecimal calculateRemainingKilos(BigDecimal totalKilos, List<SaleInCatchDTO> sales) {
        BigDecimal totalKilosSold = sales.stream()
                .map(SaleInCatchDTO::getKilosSold)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Start with BigDecimal.ZERO and use BigDecimal::add

        return totalKilos.subtract(totalKilosSold); // Use subtract method for BigDecimal
    }

}

