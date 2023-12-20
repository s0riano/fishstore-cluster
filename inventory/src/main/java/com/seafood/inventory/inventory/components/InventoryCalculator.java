package com.seafood.inventory.inventory.components;

import com.seafood.inventory.enums.SeafoodType; //might change to the shared dto
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class InventoryCalculator {

    private final CatchRepository catchRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public InventoryCalculator(CatchRepository catchRepository, SaleRepository saleRepository) {
        this.catchRepository = catchRepository;
        this.saleRepository = saleRepository;
    }

    public BigDecimal calculateCurrentInventory(UUID sellerId, SeafoodType seafoodType) {
        BigDecimal totalCaught = catchRepository.sumKilosByShopIdAndSeafoodType(sellerId, seafoodType);
        BigDecimal totalSold = saleRepository.sumKilosByShopIdAndSeafoodType(sellerId, seafoodType);

        totalCaught = totalCaught != null ? totalCaught : BigDecimal.ZERO;
        totalSold = totalSold != null ? totalSold : BigDecimal.ZERO;

        return totalCaught.subtract(totalSold);
    }
}