package com.fishtore.inventory.staticinventory.inventory;

import com.fishtore.inventory.staticinventory.enums.SeafoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findBySellerIdAndSeafoodType(Long sellerId, SeafoodType seafoodType); //has to be changed to shop and uuid....

    List<Inventory> findBySellerId(Long sellerId);
}
