package com.seafood.inventory.components;

import com.seafood.inventory.dto.InventoryDTO;
import com.seafood.inventory.sale.SaleService;
import com.seafood.inventory.sellercatch.CatchService;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DisplayInventoryForSaleComponent {

    private final CatchService catchService;
    private final SaleService saleService;

    public InventoryDTO calculateInventoryAvailableForSaleForUser(UUID userId) {
        return null;
    }
}
