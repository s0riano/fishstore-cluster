package com.fishtore.inventory.staticinventory.payload;

import com.fishtore.inventory.staticinventory.dto.TransactionItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessagePayload { //duped item
    private Long transactionId;
    private List<TransactionItemDTO> items;
}
