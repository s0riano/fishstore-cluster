package com.seafood.buyer.model.repository;

import com.seafood.buyer.model.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {
}

