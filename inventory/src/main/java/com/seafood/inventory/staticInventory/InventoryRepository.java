package com.seafood.inventory.staticInventory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, UUID> {
    List<Inventory> findByStoreId(UUID storeId);
    List<Inventory> findBySellingDate(LocalDate sellingDate);
    List<Inventory> findByStoreIdAndSellingDate(UUID storeId, LocalDate sellingDate);
    List<Inventory> findAllByStoreId(UUID storeId);
    List<Inventory> findAllByInventoryId(UUID inventoryId);
    Optional<Inventory> findById(UUID id);
}
