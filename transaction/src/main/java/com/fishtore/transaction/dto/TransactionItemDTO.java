package com.fishtore.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItemDTO {
    private String seafoodType;
    private BigDecimal kilos;
    private BigDecimal pricePerKilo;
}
/*https://www.miinto.no/p-stilige-brune-trenchcoats-for-menn-41a962c5-e877-4818-b3f8-f930e9bdebc2*/