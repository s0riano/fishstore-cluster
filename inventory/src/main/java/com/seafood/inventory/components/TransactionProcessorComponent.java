package com.seafood.inventory.components;

import com.seafood.inventory.dto.transaction.TransactionItemDTO;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.sellercatch.CatchRepository;
import com.seafood.inventory.sellercatch.Catch;
import com.seafood.inventory.sale.Sale;
import com.seafood.inventory.sale.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class TransactionProcessorComponent {

    private final CatchRepository catchRepository;
    private final SaleRepository saleRepository;
    private final UUIDGeneratorComponent uuidGenerator;

    @Autowired
    public TransactionProcessorComponent(CatchRepository catchRepository, SaleRepository saleRepository, UUIDGeneratorComponent uuidGenerator) {
        this.catchRepository = catchRepository;
        this.saleRepository = saleRepository;
        this.uuidGenerator = uuidGenerator;
    }

    // processTransaction ensures that all operations within this method are part of a single database transaction.
    // If any exception occurs or if there isn't enough inventory midways,
    // the transaction will be rolled back automatically, ensuring data consistency.
    // @Transactional ensures that if any part of the transaction fails, the entire transaction is rolled back.
    // We don't want failed sales to reduce a sellers inventory
    @Transactional
    public boolean processTransaction(TransactionRequestDTO transactionDTO) {
        List<Sale> salesToCreate = new ArrayList<>();
        UUID transactionId = transactionDTO.getTransactionId();

        for (TransactionItemDTO item : transactionDTO.getItems()) {
            List<Sale> salesForItem = allocateInventoryForItem(item, transactionDTO.getShopId(), transactionId);
            if (salesForItem == null) {
                return false;
            }
            salesToCreate.addAll(salesForItem);
        }

        try {
            saleRepository.saveAll(salesToCreate);
            log.error("These sales were saved: {}", salesToCreate);
            return true;
        } catch (Exception e) {
            log.info("Error saving sales: {}", e.getMessage(), e);
            throw e; // Re-throwing the exception ensures that the transaction is rolled back
        }
    }

    private List<Sale> allocateInventoryForItem(TransactionItemDTO item, UUID shopId, UUID transactionId) {
        try {
            List<Catch> catches = catchRepository.findByShopIdAndSeafoodTypeSortedByExpiryDate(shopId, item.getSeafoodType());
            BigDecimal requiredKilos = item.getKilos();
            List<Sale> salesForItem = new ArrayList<>();

            for (Catch c : catches) {
                if (requiredKilos.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }
                BigDecimal soldKilos = saleRepository.sumKilosForCatch(c.getCatchId()); //if null should be made 0?
                BigDecimal availableKilos = c.getKilos().subtract(soldKilos != null ? soldKilos : BigDecimal.ZERO);
                BigDecimal allocatedKilos = availableKilos.min(requiredKilos);

                Sale sale = createSaleRecord(c, allocatedKilos, transactionId);
                salesForItem.add(sale);

                requiredKilos = requiredKilos.subtract(allocatedKilos);
             }

            if (requiredKilos.compareTo(BigDecimal.ZERO) > 0) {
                return null; // Not enough inventory for this item
            }

            return salesForItem;
        } catch (Exception e) {
            log.error("Error processing inventory allocation for item: {}", item, e);
            return null;
        }
    }

    private Sale createSaleRecord(Catch c, BigDecimal allocatedKilos, UUID transactionId) {
        Sale sale = new Sale();
        sale.setSaleId(UUID.randomUUID());
        sale.setTransactionId(transactionId);
        sale.setCatchEntity(c);
        sale.setKilos(allocatedKilos);
        sale.setSaleDate(LocalDate.now());

        if (c != null && c.getCatchId() != null) {
            sale.setCatchId(c.getCatchId());
        } else {
            log.error("Catch or Catch ID is null for Sale record");
            // Handle this situation appropriately, maybe throw an exception or return null
        }

        return sale;
    }

}
