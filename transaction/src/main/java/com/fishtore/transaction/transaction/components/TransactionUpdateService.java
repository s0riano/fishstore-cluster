package com.fishtore.transaction.transaction.components;

import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionProcessingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class TransactionUpdateService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TransactionUpdateService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void updateTransactionStatus(UUID transactionId, TransactionProcessingStatus status) {
        Query query = new Query(Criteria.where("transactionId").is(transactionId));
        Update update = new Update().set("status", status);
        mongoTemplate.findAndModify(query, update, Transaction.class);
        log.info("Transaction with ID: {} updated to {}", transactionId, status);
    }
}