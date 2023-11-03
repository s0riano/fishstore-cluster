package com.fishtore.transaction.staticinventory.transaction.components;

import com.fishtore.transaction.staticinventory.dto.TransactionDTO;
import com.fishtore.transaction.staticinventory.dto.TransactionItemDTO;
import com.fishtore.transaction.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.transaction.staticinventory.transaction.Transaction;
import com.fishtore.transaction.staticinventory.transaction.TransactionItem;
import com.fishtore.transaction.staticinventory.transaction.TransactionStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class OrderPlacementComponent {

    public String handleOrderPlacement(TransactionDTO transactionDTO) {
        UUID transactionId = UUID.randomUUID();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId.toString());

        List<TransactionItemDTO> itemDTOs = transactionDTO.getItems();
        List<TransactionItem> items = itemDTOs.stream()
                .map(dto -> {
                    TransactionItem item = new TransactionItem();
                    item.setSeafoodType(dto.getSeafoodType());
                    item.setKilos(dto.getKilos());
                    return item;
                }).toList();
        transaction.setItems(items);

        BigDecimal totalKilos = items.stream()
                .map(TransactionItem::getKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        transaction.setKilos(totalKilos);

        transaction.setStatus(TransactionStatus.PENDING);

        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setTransactionId(transactionId.toString());
        requestDTO.setItems(transactionDTO.getItems());

        return "Order placed with ID: " + transactionId + ". Awaiting confirmation.";
    }
}
