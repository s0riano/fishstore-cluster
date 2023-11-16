package com.fishtore.transaction.components;

import com.fishstore.shared.dto.payload.InventoryResponsePayload;
import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionRepository;
import com.fishtore.transaction.transaction.TransactionStatus;
import com.fishtore.transaction.transaction.components.TransactionUpdateService;
import com.fishtore.transaction.transaction.failures.FailedTransactionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryResponseComponent {

    private final TransactionRepository transactionRepository;
    private final InventoryDoubleCheckComponent inventoryDoubleCheckComponent;
    private final FailedTransactionHandler failedTransactionHandler;
    private final TransactionUpdateService transactionUpdateService;

    @Autowired
    public InventoryResponseComponent(
            TransactionRepository transactionRepository,
            InventoryDoubleCheckComponent inventoryDoubleCheckComponent,
            FailedTransactionHandler failedTransactionHandler,
            TransactionUpdateService transactionUpdateService
    ) {
        this.transactionRepository = transactionRepository;
        this.inventoryDoubleCheckComponent = inventoryDoubleCheckComponent;
        this.failedTransactionHandler = failedTransactionHandler;
        this.transactionUpdateService = transactionUpdateService;
    }

    public void updateStatusFromInventory(InventoryResponsePayload payload) { //change name?

        Transaction transaction = transactionRepository.findByTransactionId(payload.getTransactionId()).orElse(null);
        if (transaction != null) {
            try {
                TransactionStatus newStatus = payload.isAvailable() ? TransactionStatus.RESERVED : TransactionStatus.INSUFFICIENT_INVENTORY;
                transactionUpdateService.updateTransactionStatus(payload.getTransactionId(), newStatus);//NOTE THAT THE TRANSACTION WAS DOUBLE-CHECKED!
            } catch (Exception e) {
                String errorMessage = "Error updating transaction status for transaction ID: " + payload.getTransactionId() + " - " + e.getMessage();
                log.error(errorMessage, e);
                failedTransactionHandler.logFailedTransaction(payload.getTransactionId(), TransactionStatus.CANT_FIND_TRANSACTION_ID, errorMessage);
            }
        } else {
            // Transaction not found, handle accordingly
            log.warn("Transaction with ID: {} not found", payload.getTransactionId());
            inventoryDoubleCheckComponent.doubleCheckTransaction(payload.getTransactionId(), payload.isAvailable());
        }
    }
}