package com.fishtore.inventory.staticinventory.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findBySellerIdAndSeafoodType(Long sellerId, SeafoodType seafoodType);

    List<Inventory> findBySellerId(Long sellerId);
}
