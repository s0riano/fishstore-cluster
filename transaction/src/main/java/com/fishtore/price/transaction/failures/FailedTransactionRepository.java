package com.fishtore.price.transaction.failures;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedTransactionRepository extends MongoRepository<FailedTransaction, String> {
    // Custom query methods if required
}

