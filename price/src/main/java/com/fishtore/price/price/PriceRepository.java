package com.fishtore.price.price;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PriceRepository extends MongoRepository<Price, Long> {
    Optional<Price> findBySellerId(Long sellerId);
}
