package com.fishtore.transaction.transaction.components;

import com.fishtore.transaction.components.PriceVerificationComponent;
import com.fishtore.transaction.service.RabbitMQService;
import com.fishtore.transaction.transaction.*;
import dto.TransactionItemDTO;
import dto.preorder.PreOrderDTO;
import dto.preorder.PreOrderTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PreOrderPlacementComponent {

    private final PriceVerificationComponent priceVerificationComponent;
    private final RabbitMQService rabbitMQService;
    private final TransactionRepository transactionRepository;

    public PreOrderPlacementComponent(PriceVerificationComponent priceVerificationComponent, RabbitMQService rabbitMQService, TransactionRepository transactionRepository) {
        this.priceVerificationComponent = priceVerificationComponent;
        this.rabbitMQService = rabbitMQService;
        this.transactionRepository = transactionRepository;
    }


    public String handlePreOrder(PreOrderDTO preOrderDTO) { //TODO: create an object (class) to return instead of string
        Transaction transaction;
        try {
            transaction = createTransactionFromPreOrder(preOrderDTO);
        } catch (IllegalArgumentException e) {
            return "Order placement failed: " + e.getMessage();
        }

        try {
            PriceStatus pricesAreValid = priceVerificationComponent
                    .verifyTransactionPrices(transaction, preOrderDTO.getShopId());
            transaction.setPriceStatus(pricesAreValid);
            log.info("Price check response: " + pricesAreValid);

            //TODO: Handle I/O error on GET request for price

        } catch (Exception e) {
            transaction.setPriceStatus(PriceStatus.API_CALL_ERROR);
        }

        try {
            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Order placed with ID: {}. Transaction status: {}", savedTransaction.getTransactionId(), transaction.getStatus());

            if (savedTransaction.getPriceStatus() == PriceStatus.PRICE_VERIFIED_AND_MATCHES) {
                PreOrderTransactionDTO transactionRequestDTO = new PreOrderTransactionDTO(
                        savedTransaction.getShopId(),
                        transaction.getTransactionId(),
                        preOrderDTO.getInventoryId(),
                        preOrderDTO.getItems()
                );

                rabbitMQService.sendPreOrderCheckMessage(transactionRequestDTO);
            } else {
                log.error("Price mismatch for transaction ID: {}", savedTransaction.getTransactionId());
                savedTransaction.setStatus(TransactionProcessingStatus.PRICE_MISMATCH);
                transactionRepository.save(savedTransaction);
                return "Order placement failed: Price mismatch.";
            }

            return "Order placed with ID: " + transaction.getTransactionId() + ". Awaiting confirmation.";
        } catch (Exception e) {
            log.error("Error while placing the order: " + e.getMessage());
            // throw new OrderPlacementException("An error occurred while placing the order.", e);
            return "Order placement failed: An error occurred.";
        }


    }

    private BigDecimal calculateTotalKilos(List<TransactionItem> items) {
        return items.stream()
                .map(TransactionItem::getKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Transaction createTransactionFromPreOrder(PreOrderDTO preOrderDTO) {
        List<TransactionItemDTO> itemDTOs = preOrderDTO.getItems();
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            throw new IllegalArgumentException("No items provided.");
        }

        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setShopId(preOrderDTO.getShopId());
        transaction.setBuyerId(preOrderDTO.getBuyerId());

        List<TransactionItem> items = itemDTOs.stream()
                .map(dto -> new TransactionItem(dto.getSeafoodType(), dto.getKilos(), dto.getPricePerKilo()))
                .collect(Collectors.toList());
        transaction.setItems(items);

        transaction.updateTotalPrice();
        transaction.setKilos(calculateTotalKilos(items));
        transaction.setStatus(TransactionProcessingStatus.PENDING);
        transaction.setPriceStatus(PriceStatus.NOT_CHECKED);

        return transaction;
    }
}