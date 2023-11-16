package com.seafood.inventory.dto.transaction;

import com.fishstore.shared.dto.TransactionItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO { //checking price and checking inventory
    private String transactionId;
    private Long sellerId;
    private List<TransactionItemDTO> items;
}