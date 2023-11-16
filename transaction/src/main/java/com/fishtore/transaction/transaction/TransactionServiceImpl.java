package com.fishtore.transaction.transaction;

import com.fishstore.shared.dto.TransactionDTO;
import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishstore.shared.dto.payload.InventoryResponsePayload;
import com.fishtore.transaction.transaction.components.OrderPlacementComponent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, OrderPlacementComponent orderPlacementComponent) {
        this.transactionRepository = transactionRepository;
        this.orderPlacementComponent = orderPlacementComponent;
    }

    private final OrderPlacementComponent orderPlacementComponent;

    @Override
    public String processOrderPlacement(TransactionDTO transactionDTO) {
        return orderPlacementComponent.handleOrderPlacement(transactionDTO);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransactionStatus(String transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction with ID: " + transactionId + " not found"));
        transaction.setStatus(status);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public void requestItemAvailability(TransactionRequestDTO transactionRequestDTO) {

    }

    @Override
    public void sendInventoryCheckMessage(Transaction transaction) {
        Map<String, Object> messagePayload = new HashMap<>();
        messagePayload.put("transactionId", transaction.getTransactionId());
        messagePayload.put("items", transaction.getItems());
        // Send the message
        rabbitTemplate.convertAndSend("inventoryExchange", "inventoryCheck", messagePayload);
    }

    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "transactionResponseQueue", durable = "true"),
            exchange = @Exchange(name = "transactionExchange"),
            key = "inventoryResponse"))*/
    public void processInventoryResponse(InventoryResponsePayload responsePayload) {
        String transactionId = responsePayload.getTransactionId();
        boolean isAvailable = responsePayload.isAvailable();
        //also update the transaction with the payload???
    }

    @Override
    public List<Transaction> getAllTransactions() {
        //logger.info("Fetched transactions: " + transactions.size());
        return transactionRepository.findAll();
    }
}

