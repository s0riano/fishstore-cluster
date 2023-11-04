package com.fishtore.transaction.notification;

import com.fishtore.transaction.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyPriceDiscrepancy(Transaction transaction) {
        // You can send an email, update a dashboard, etc.
        // For now, I'm just logging the discrepancy.

        logger.warn("Price discrepancy detected for transaction ID: {}", transaction.getTransactionId());
    }
}