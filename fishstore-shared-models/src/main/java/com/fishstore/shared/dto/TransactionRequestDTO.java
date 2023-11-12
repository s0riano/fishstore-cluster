package com.fishstore.shared.dto;

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
