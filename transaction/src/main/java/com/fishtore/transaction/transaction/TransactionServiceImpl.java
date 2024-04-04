package com.fishtore.transaction.transaction;



import dto.TransactionDTO;

import com.fishtore.transaction.transaction.components.OrderPlacementComponent;
import com.fishtore.transaction.transaction.components.PreOrderPlacementComponent;
import dto.TransactionRequestDTO;
import dto.payload.InventoryResponsePayload;
import dto.preorder.PreOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, OrderPlacementComponent orderPlacementComponent, PreOrderPlacementComponent preOrderPlacementComponent) {
        this.transactionRepository = transactionRepository;
        this.orderPlacementComponent = orderPlacementComponent;
        this.preOrderPlacementComponent = preOrderPlacementComponent;
    }

    private final OrderPlacementComponent orderPlacementComponent;
    private final PreOrderPlacementComponent preOrderPlacementComponent;

    @Override
    public String processOrderPlacement(TransactionDTO transactionDTO) {
        return orderPlacementComponent.handleOrderPlacement(transactionDTO);
    }

    @Override
    public String processPreOrder(PreOrderDTO dto) {
        return preOrderPlacementComponent.handlePreOrder(dto);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction with ID: " + transactionId + " not found"));
        transaction.setStatus(status);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(UUID transactionId) {
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
        UUID transactionId = responsePayload.getTransactionId();
        boolean isAvailable = responsePayload.isAvailable();
        //also update the transaction with the payload???
    }

    @Override
    public List<Transaction> getAllTransactions() {
        //logger.info("Fetched transactions: " + transactions.size());
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void updatePickupTimestamp(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        transaction.setPickupTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.COMPLETED);
        log.info("Transaction {}, was now picked up at: {}", transactionId, transaction.getPickupTimestamp());
        transactionRepository.save(transaction);
    }
}

