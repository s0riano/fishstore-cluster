package com.seafood.inventory.staticInventory;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaticInventoryRepository extends MongoRepository<Inventory, UUID> {
    List<Inventory> findByShopId(UUID storeId);
    List<Inventory> findBySellingDate(LocalDate sellingDate);
    List<Inventory> findByShopIdAndSellingDate(UUID storeId, LocalDate sellingDate);
    List<Inventory> findAllByShopId(UUID storeId);
    List<Inventory> findAllByInventoryId(UUID inventoryId);
    @NotNull
    Optional<Inventory> findById(@NotNull UUID id);
    Optional<Inventory> findByInventoryIdAsString(String id);
    Optional<Inventory> findByInventoryId(UUID id);
}
