package com.fishtore.transaction.crossserviceoperations.preorder;

import com.fishtore.transaction.components.PriceVerificationComponent;
import com.fishtore.transaction.dto.TransactionItemDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.dto.preorder.PreOrderRequestDTO;
import com.fishtore.transaction.dto.preorder.PreOrderTransactionDTO;
import com.fishtore.transaction.service.RabbitMQService;
import com.fishtore.transaction.transaction.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PreOrderHandler {

    private final PriceVerificationComponent priceVerificationComponent;
    private final RabbitMQService rabbitMQService;
    private final TransactionRepository transactionRepository;


    @Autowired
    public PreOrderHandler(PriceVerificationComponent priceVerificationComponent, RabbitMQService rabbitMQService, TransactionRepository transactionRepository) {
        this.priceVerificationComponent = priceVerificationComponent;
        this.rabbitMQService = rabbitMQService;
        this.transactionRepository = transactionRepository;
    }

    public String handle(PreOrderTransactionDTO preOrderTransactionDTO) { //make this to a void? return errors instead of strings
        TransactionCreationResponse response = formatDtoToTransaction(preOrderTransactionDTO);
        Transaction transaction = response.getTransaction();

        try {
            PriceStatus pricesAreValid = priceVerificationComponent
                    .verifyTransactionPrices(transaction, preOrderTransactionDTO.getShopId());
            transaction.setPriceStatus(pricesAreValid);
        } catch (Exception e) {
            transaction.setPriceStatus(PriceStatus.API_CALL_ERROR);
        }

        try { //Saving the initial draft to the database to keep a record
            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Order placed with ID: {}. Transaction status: {}", savedTransaction.getTransactionId(), transaction.getStatus());

            if (transaction.getPriceStatus() != PriceStatus.PRICE_VERIFIED_AND_MATCHES){
                return "Something went wrong with checking the price of your order, we are terribly sorry";
            }

            if (savedTransaction.getPriceStatus() == PriceStatus.PRICE_VERIFIED_AND_MATCHES) {
                PreOrderRequestDTO transactionRequestDTO = new PreOrderRequestDTO(
                        transaction.getTransactionId(),
                        transaction.getShopId(),
                        transaction.getInventoryId(),
                        preOrderTransactionDTO.getItems()
                );
                rabbitMQService.sendPreOrderRequestMessage(transactionRequestDTO); //TODO: Fix the listener in inventory

            } else {
                log.error("Price mismatch for transaction ID: {}", savedTransaction.getTransactionId());
                savedTransaction.setStatus(TransactionStatus.PRICE_MISMATCH); //move down to inline variable
                transactionRepository.save(savedTransaction);
                return "Order placement failed: Price mismatch.";
            }

            return "Order placed with ID: " + transaction.getTransactionId() + ". Awaiting confirmation.";
        } catch (Exception e) {
            log.error("Error while placing the order: " + e.getMessage());
            return "Order placement failed: An error occurred.";
        }
    }

    private TransactionCreationResponse formatDtoToTransaction(PreOrderTransactionDTO preOrderTransactionDTO) {
        List<TransactionItemDTO> itemDTOs = preOrderTransactionDTO.getItems();
        if (itemDTOs == null || itemDTOs.isEmpty()) { //TODO: add check for this in handler
            return TransactionCreationResponse.withErrorMessage("Order placement failed: No items provided.");
        }

        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setShopId(preOrderTransactionDTO.getShopId());
        //transaction.setBuyerId(preOrderTransactionDTO.getBuyerId());
        transaction.setInventoryId(preOrderTransactionDTO.getInventoryId());

        List<TransactionItem> items = itemDTOs.stream()
                .map(dto -> {
                    TransactionItem item = new TransactionItem();
                    item.setSeafoodType(dto.getSeafoodType());
                    item.setKilos(dto.getKilos());
                    item.setPricePerKilo(dto.getPricePerKilo());
                    return item;
                }).collect(Collectors.toList());
        transaction.setItems(items);

        transaction.updateTotalPrice();

        BigDecimal totalKilos = items.stream()
                .map(TransactionItem::getKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        transaction.setKilos(totalKilos);

        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setPriceStatus(PriceStatus.NOT_CHECKED);

        return TransactionCreationResponse.withTransaction(transaction);
    }


}
