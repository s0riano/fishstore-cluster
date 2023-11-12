package com.fishtore.price.dto.payload;

import com.fishtore.price.dto.TransactionItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessagePayload {

    private Long transactionId;
    private List<TransactionItemDTO> items;
}
