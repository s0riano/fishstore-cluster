package com.fishtore.transaction.staticinventory.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // any custom query methods can go here
}
