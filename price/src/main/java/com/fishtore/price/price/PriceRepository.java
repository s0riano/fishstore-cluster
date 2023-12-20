package com.fishtore.price.price;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PriceRepository extends MongoRepository<Price, String> {
    @NotNull
    Optional<Price> findById(@NotNull String shopId);
}
