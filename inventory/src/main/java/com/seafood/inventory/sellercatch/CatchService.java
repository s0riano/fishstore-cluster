package com.seafood.inventory.sellercatch;

import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.sale.Sale;
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.shared.CatchNotFoundException;
import com.seafood.inventory.shared.InsufficientStockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CatchService {

    @Autowired
    private CatchRepository catchRepository;

    @Autowired
    private SaleRepository saleRepository;

    public List<Catch> getAllCatches() {
        return catchRepository.findAll();
    }

    public Catch getCatchById(UUID id) {
        return catchRepository.findById(id).orElse(null);
    }

    public Catch saveCatch(Catch catchData) {
        return catchRepository.save(catchData);
    }

    public void deleteCatch(UUID id) {
        catchRepository.deleteById(id);
    }

    public List<Catch> getCatchesBySellerId(UUID sellerId) {
        return catchRepository.findByShopId(sellerId);
    }

    public Map<SeafoodType, Float> getAvailableKilosBySellerId(UUID sellerId) {
        List<Object[]> results = catchRepository.getInventoryForShop(sellerId);
        Map<SeafoodType, Float> availableKilos = new HashMap<>();

        for (Object[] result : results) {
            SeafoodType type = (SeafoodType) result[0];
            Float kilos = ((Number) result[1]).floatValue();
            availableKilos.put(type, kilos);
        }

        return availableKilos;
    }   //this sucks

    @Transactional
    public void registerSale(UUID catchId, BigDecimal kilosSold) {
        Catch catchData = catchRepository.findById(catchId).orElse(null);
        if (catchData != null) {
            if (catchData.getKilos().compareTo(kilosSold) >= 0) { // Using compareTo for comparison
                Sale sale = new Sale();
                sale.setCatchEntity(catchData);
                sale.setKilos(kilosSold); // Directly use BigDecimal if your Sale class's kilos field is a BigDecimal
                sale.setSaleDate(LocalDate.now());
                saleRepository.save(sale);

                catchData.setKilos(catchData.getKilos().subtract(kilosSold)); // Using subtract method for BigDecimal
                catchRepository.save(catchData);
            } else {
                throw new InsufficientStockException("Requested kilos exceeds available stock for catch ID: " + catchId);
            }
        } else {
            throw new CatchNotFoundException("No catch found with ID: " + catchId);
        }
        /*
        If everything goes smoothly, the method will complete, and the sale will be registered.
        However, if there's any issue (like insufficient stock or a non-existent catch ID),
        the method will throw an exception, and due to the @Transactional annotation,
        all changes will be rolled back.
        In essence, this method ensures that a sale is registered only if there's enough stock,
        and it keeps both the sale data and the catch data consistent.
        */ // also, i changed to bigDecimal from float, so i am looking for errors
    }
}
