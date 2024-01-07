package com.fishtore.transaction.transaction.components;

import com.fishtore.transaction.components.PriceVerificationComponent;
import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.dto.TransactionItemDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.service.RabbitMQService;
import com.fishtore.transaction.transaction.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Component
@Slf4j
public class OrderPlacementComponent { //where should the payment logic be? should it even be here?
    //private final PriceVerificationService priceVerificationService;
    private final PriceVerificationComponent priceVerificationComponent;
    private final RabbitMQService rabbitMQService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderPlacementComponent(PriceVerificationComponent priceVerificationComponent, RabbitMQService rabbitMQService, TransactionRepository transactionRepository) {
        this.priceVerificationComponent = priceVerificationComponent;
        this.rabbitMQService = rabbitMQService;
        this.transactionRepository = transactionRepository;
    }

    public String handleOrderPlacement(TransactionDTO transactionDTO) {
        List<TransactionItemDTO> itemDTOs = transactionDTO.getItems();
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            return "Order placement failed: No items provided.";
        }
        UUID transactionId = UUID.randomUUID(); // move this to a component, the init of the transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setShopId(transactionDTO.getShopId());
        transaction.setBuyerId(transactionDTO.getBuyerId());

        List<TransactionItem> items = itemDTOs.stream()
                .map(dto -> {
                    TransactionItem item = new TransactionItem();
                    item.setSeafoodType(dto.getSeafoodType());
                    item.setKilos(dto.getKilos());
                    item.setPricePerKilo(dto.getPricePerKilo());
                    return item; //if something is forgotten here, it will be set to null and errors occur
                }).toList();
        transaction.setItems(items);

        transaction.updateTotalPrice();

        BigDecimal totalKilos = items.isEmpty()
                ? BigDecimal.ZERO                  // Calculate total kilos if items are not empty
                : items.stream()
                .map(TransactionItem::getKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        transaction.setKilos(totalKilos);

        transaction.setStatus(TransactionStatus.PENDING); //CHECK LATER IF THIS IS SET AUTOMATIC
        transaction.setPriceStatus(PriceStatus.NOT_CHECKED);

        try {
            PriceStatus pricesAreValid = priceVerificationComponent.verifyTransactionPrices(transaction, transactionDTO.getShopId());
            transaction.setPriceStatus(pricesAreValid);
            //log.error("Price check response: " + pricesAreValid);
        } catch (Exception e) {
            transaction.setPriceStatus(PriceStatus.API_CALL_ERROR);
        }
        try {
            Transaction savedTransaction = transactionRepository.save(transaction);    //saving to db before initiating the asynchronous operations

            log.info("Order placed with ID: {}. Transaction status: {}", savedTransaction.getTransactionId(), transaction.getStatus());

            if (savedTransaction.getPriceStatus() == PriceStatus.PRICE_VERIFIED_AND_MATCHES) {
                TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(
                        transactionId, //fromString(savedTransaction.getTransactionId()),
                        savedTransaction.getShopId(),
                        transactionDTO.getItems()
                );
                // Send inventory check message only if prices are verified and match
                rabbitMQService.sendInventoryCheckMessage(transactionRequestDTO);
            } else {
                log.error("Price mismatch for transaction ID: {}", savedTransaction.getTransactionId());
                // savedTransaction.setStatus(TransactionStatus.PRICE_MISMATCH);
                // transactionRepository.save(savedTransaction);
                return "Order placement failed: Price mismatch.";
            }

            return "Order placed with ID: " + transactionId + ". Awaiting confirmation.";
        } catch (Exception e) {
            log.error("Error while placing the order: " + e.getMessage());
            // throw new OrderPlacementException("An error occurred while placing the order.", e);
            return "Order placement failed: An error occurred.";
        }
    }

}
