package com.fishstore.shared.dto.payload;

import com.fishstore.shared.dto.TransactionItemDTO;
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
