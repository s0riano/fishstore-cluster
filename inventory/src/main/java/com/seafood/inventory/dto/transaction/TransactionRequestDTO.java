package com.seafood.inventory.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO { //checking price and checking inventory
    private UUID transactionId;
    private UUID shopId;
    private List<TransactionItemDTO> items;
}