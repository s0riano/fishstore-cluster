package com.fishtore.transaction.transaction;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItem {
    private String seafoodType;
    private BigDecimal kilos;
    private BigDecimal pricePerKilo;
}
