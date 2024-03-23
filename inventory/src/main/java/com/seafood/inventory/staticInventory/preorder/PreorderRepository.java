package com.seafood.inventory.staticInventory.preorder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PreorderRepository extends MongoRepository<Preorder, UUID> {
    List<Preorder> findByInventoryId(UUID inventoryId);
    List<Preorder> findByTransactionId(UUID transactionId);
}