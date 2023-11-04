package com.fishtore.transaction.transaction.components;

import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.dto.TransactionItemDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.pricing.PriceVerificationService;
import com.fishtore.transaction.service.RabbitMQService;
import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionItem;
import com.fishtore.transaction.transaction.TransactionRepository;
import com.fishtore.transaction.transaction.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class OrderPlacementComponent {

    private static final Logger logger = LoggerFactory.getLogger(OrderPlacementComponent.class);
    private final PriceVerificationService priceVerificationService;
    private final RabbitMQService rabbitMQService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderPlacementComponent(PriceVerificationService priceVerificationService, RabbitMQService rabbitMQService, TransactionRepository transactionRepository) {
        this.priceVerificationService = priceVerificationService; // Assign passed-in service to the class-level field
        this.rabbitMQService = rabbitMQService;
        this.transactionRepository = transactionRepository;
    }

    public String handleOrderPlacement(TransactionDTO transactionDTO) {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId.toString());
        transaction.setSellerId(transactionDTO.getSellerId());
        transaction.setBuyerId(transactionDTO.getBuyerId());

        List<TransactionItemDTO> itemDTOs = transactionDTO.getItems();
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            return "Order placement failed: No items provided.";
        }
        List<TransactionItem> items = itemDTOs.stream()
                .map(dto -> {
                    TransactionItem item = new TransactionItem();
                    item.setSeafoodType(dto.getSeafoodType());
                    item.setKilos(dto.getKilos());
                    return item;
                }).toList();
        transaction.setItems(items);

        transaction.updateTotalPrice();

        BigDecimal totalKilos = items.isEmpty()
                ? BigDecimal.ZERO                  // Calculate total kilos if items are not empty
                : items.stream()
                .map(TransactionItem::getKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        transaction.setKilos(totalKilos);

        transaction.setStatus(TransactionStatus.PENDING); // setting it to pending before saving, might be done automatic

        try {
            transactionRepository.save(transaction);    //saving to db before initiating the asynchronous operations
            logger.info("Order placed with ID: {}. Transaction status: {}", transactionId, transaction.getStatus());

            TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(
                    transactionId.toString(),
                    transactionDTO.getSellerId(),   // constructing the DTO for requesting inventory & price
                    transactionDTO.getItems()
            );
            rabbitMQService.sendInventoryCheckMessage(transactionRequestDTO);   //checking for availability
                        //also remember to make a check price
            return "Order placed with ID: " + transactionId + ". Awaiting confirmation.";
        } catch (Exception e) {
            logger.error("Error while placing the order: " + e.getMessage());
            //  can rethrow the exception or wrap it in a custom exception
            // throw new OrderPlacementException("An error occurred while placing the order.", e);
            return "Order placement failed: An error occurred.";
        }
    }

}

