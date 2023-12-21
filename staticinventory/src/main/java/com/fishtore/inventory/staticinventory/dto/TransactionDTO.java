package com.fishtore.inventory.staticinventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long buyerId;
    private Long sellerId;
    private List<TransactionItemDTO> items;
}
