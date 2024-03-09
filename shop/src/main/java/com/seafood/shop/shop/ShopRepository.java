package com.seafood.shop.shop;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    boolean existsByOwnersUserId(UUID userId);
    /*@Query("SELECT s FROM Shop s JOIN s.owners o WHERE s.isActive = true AND o.isActive = true")
    List<Shop> findAllActiveShopsWithActiveOwner();
*/
}