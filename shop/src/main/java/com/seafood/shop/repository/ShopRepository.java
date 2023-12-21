package com.seafood.shop.repository;


import com.seafood.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    boolean existsByOwnersUserId(UUID userId);
    /*@Query("SELECT s FROM Shop s JOIN s.owners o WHERE s.isActive = true AND o.isActive = true")
    List<Shop> findAllActiveShopsWithActiveOwner();
*/
}