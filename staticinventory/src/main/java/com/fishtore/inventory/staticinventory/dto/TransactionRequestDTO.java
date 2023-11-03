package com.fishtore.inventory.staticinventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRequestDTO {
    private String transactionId; // assuming you're storing UUID as a String
    private List<TransactionItemDTO> items;
}
