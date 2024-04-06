package com.seafood.seller.repository;

import com.seafood.seller.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
    @Query("SELECT s FROM Seller s")
    List<Seller> fetchAllSellers();

    boolean existsByEmail(String email);

    boolean existsById(UUID id);
}