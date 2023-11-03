package com.fishtore.transaction.staticinventory.transaction;

import com.fishtore.transaction.staticinventory.dto.TransactionDTO;
import com.fishtore.transaction.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.transaction.staticinventory.payload.InventoryResponsePayload;
import com.fishtore.transaction.staticinventory.transaction.components.OrderPlacementComponent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    public String processOrderPlacement(TransactionDTO transactionDTO) { //so big I moved it to its own component
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
        // Create the message payload
        Map<String, Object> messagePayload = new HashMap<>();
        messagePayload.put("transactionId", transaction.getTransactionId());
        messagePayload.put("items", transaction.getItems());

        // Send the message
        rabbitTemplate.convertAndSend("inventoryExchange", "inventoryCheck", messagePayload);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "transactionResponseQueue", durable = "true"),
            exchange = @Exchange(name = "transactionExchange"),
            key = "inventoryResponse"))
    public void processInventoryResponse(InventoryResponsePayload responsePayload) {
        Long transactionId = responsePayload.getTransactionId();
        boolean isAvailable = responsePayload.isAvailable();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

