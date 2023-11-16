package com.seafood.inventory.inventory.components;

import com.seafood.inventory.enums.SeafoodType; //might change to the shared dto
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryCalculator {

    private final CatchRepository catchRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public InventoryCalculator(CatchRepository catchRepository, SaleRepository saleRepository) {
        this.catchRepository = catchRepository;
        this.saleRepository = saleRepository;
    }

    public float calculateCurrentInventory(Long sellerId, SeafoodType seafoodType) {
        Float totalCaught = catchRepository.sumKilosBySellerIdAndSeafoodType(sellerId, seafoodType);
        Float totalSold = saleRepository.sumKilosBySellerIdAndSeafoodType(sellerId, seafoodType);
        return (totalCaught != null ? totalCaught : 0) - (totalSold != null ? totalSold : 0);
    }
}