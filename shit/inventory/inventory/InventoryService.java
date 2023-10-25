package com.seafood.inventory.inventory;

import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.sale.Sale;
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.Catch;
import com.seafood.inventory.sellercatch.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {

    @Autowired
    private CatchRepository catchRepository;

    @Autowired
    private SaleRepository saleRepository;


    public Map<SeafoodType, Float> getAvailableKilosBySellerId(Long sellerId) {
        List<Object[]> results = catchRepository.getInventoryForSeller(sellerId);
        Map<SeafoodType, Float> availableKilos = new HashMap<>();

        for (Object[] result : results) {
            SeafoodType type = (SeafoodType) result[0];
            Float kilos = ((Number) result[1]).floatValue();
            availableKilos.put(type, kilos);
        }

        return availableKilos;
    }


    public Map<Long, Float> getInventoryBySellerId(Long sellerId) {
        Map<Long, Float> inventory = new HashMap<>();

        //Fetch all catches for the given seller.
        List<Catch> catchesForSeller = catchRepository.findBySellerId(sellerId);

        for (Catch catchData : catchesForSeller) {
            //For each catch, fetch any sales associated with that catch.
            List<Sale> salesForCatch = saleRepository.findByCatchEntityCatchId(catchData.getCatchId());

            float totalSalesFromCatch = salesForCatch.stream()
                    .map(Sale::getKilos)
                    .reduce(0f, Float::sum);

            //Subtract the total sales from the catch to get the remaining inventory for that catch.
            float remainingKilos = catchData.getKilos() - totalSalesFromCatch;

            inventory.put(catchData.getCatchId(), remainingKilos);
        }

        return inventory;
    }
}
