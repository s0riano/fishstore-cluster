package com.fishtore.inventory.staticinventory.inventory;

import com.fishstore.shared.dto.SeafoodType;
import com.fishstore.shared.dto.TransactionItemDTO;
import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.dto.PriceInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//@Service
public interface InventoryService {

    List<Inventory> getAllInventories();

    Inventory getInventoryById(Long id);

    Inventory createInventory(Inventory inventory);
    @Transactional //hmm
    Inventory updateInventory(Inventory inventory);

    void processFetchItems(List<TransactionItemDTO> items);

    void processInventoryCheck(TransactionRequestDTO payload);

    public List<PriceInfoDTO> findPricesBySeller(Long sellerId);

    boolean checkAndReserveInventory(TransactionRequestDTO transactionRequestDTO);

    Inventory findBySellerIdAndSeafoodType(Long sellerId, SeafoodType valueOf);
    // Your logic to check inventory based on the received DTO
        // For example:
        // 1. Extract items from the DTO
        // 2. Check if each item is available in the required quantity
        // 3. Update the inventory if necessary
        // 4. Send a response back to the Transaction Service (if needed)

}

