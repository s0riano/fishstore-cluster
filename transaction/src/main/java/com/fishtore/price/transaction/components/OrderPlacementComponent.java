package com.fishtore.price.transaction.components;

import com.fishstore.shared.dto.TransactionDTO;
import com.fishstore.shared.dto.TransactionItemDTO;
import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishtore.price.components.PriceVerificationComponent;
import com.fishtore.price.service.RabbitMQService;
import com.fishtore.price.transaction.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class OrderPlacementComponent {
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
        transaction.setTransactionId(transactionId.toString());
        transaction.setSellerId(transactionDTO.getSellerId());
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
            PriceStatus pricesAreValid = priceVerificationComponent.verifyTransactionPrices(transaction, transactionDTO.getSellerId());
            transaction.setPriceStatus(pricesAreValid);
            //log.error("Price check response: " + pricesAreValid);
        } catch (Exception e) {
            transaction.setPriceStatus(PriceStatus.API_CALL_ERROR);
        }

        try {
            Transaction savedTransaction = transactionRepository.save(transaction);    //saving to db before initiating the asynchronous operations

            log.info("Order placed with ID: {}. Transaction status: {}", savedTransaction.getTransactionId(), transaction.getStatus());

            TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(
                    savedTransaction.getTransactionId(), //change to object id? but not received from mngo
                    savedTransaction.getSellerId(),   // constructing the DTO for requesting inventory & price
                    transactionDTO.getItems()
            );
            rabbitMQService.sendInventoryCheckMessage(transactionRequestDTO);   //checking for availability

            return "Order placed with ID: " + transactionId + ". Awaiting confirmation.";
        } catch (Exception e) {
            log.error("Error while placing the order: " + e.getMessage());
            // throw new OrderPlacementException("An error occurred while placing the order.", e);
            return "Order placement failed: An error occurred.";
        }
    }

}
