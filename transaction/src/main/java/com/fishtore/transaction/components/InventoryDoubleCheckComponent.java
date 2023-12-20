package com.fishtore.transaction.components;

import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionRepository;
import com.fishtore.transaction.transaction.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class InventoryDoubleCheckComponent {

    private final TransactionRepository transactionRepository;

    @Autowired
    public InventoryDoubleCheckComponent(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async
    public void doubleCheckTransaction(UUID transactionId, boolean isAvailable) {
        log.warn("Transaction {} not found. We are now double checking it", transactionId);
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);

        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            transaction.setStatus(isAvailable ? TransactionStatus.RESERVED : TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            log.info("Transaction status updated on double check for ID: {} - its status is now {}", transactionId, transaction.getStatus() );
        } else {
            log.warn("Transaction still not found on double check for ID: {} - it might need hands-on treatment", transactionId);
        }
    }
}
