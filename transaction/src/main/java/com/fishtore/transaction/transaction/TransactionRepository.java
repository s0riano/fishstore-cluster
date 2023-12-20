package com.fishtore.transaction.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends MongoRepository<Transaction, UUID> {
    // any custom query methods can go here
    Optional<Transaction> findByTransactionId(UUID transactionId);//Optional incase there is an error
}
