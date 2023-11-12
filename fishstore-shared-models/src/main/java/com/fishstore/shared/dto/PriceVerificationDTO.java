package com.fishstore.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceVerificationDTO {
    private String transactionId;
    private List<TransactionItemDTO> items;
}

