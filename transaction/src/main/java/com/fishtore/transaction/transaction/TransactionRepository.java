package com.fishtore.transaction.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // any custom query methods can go here
    Optional<Transaction> findByTransactionId(String transactionId);//Optional incase there is an error
}
