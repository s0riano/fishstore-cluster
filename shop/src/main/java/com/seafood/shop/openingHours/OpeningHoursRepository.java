package com.seafood.shop.openingHours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, UUID> {
    // You can add custom methods here if needed
}