package com.seafood.demo.repository;

import com.seafood.demo.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s")
    List<Seller> fetchAllSellers();
}