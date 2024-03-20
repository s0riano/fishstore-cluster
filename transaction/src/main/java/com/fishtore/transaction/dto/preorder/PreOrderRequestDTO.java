package com.fishtore.transaction.dto.preorder;

import com.fishtore.transaction.dto.TransactionItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreOrderRequestDTO {
    private UUID transactionId;
    private UUID inventoryId;
    private UUID shopId;
    private List<TransactionItemDTO> items;
}
