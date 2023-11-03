package com.fishtore.transaction.staticinventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRequestDTO {
    private String transactionId;
    private List<TransactionItemDTO> items;
}
