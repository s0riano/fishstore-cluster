package com.seafood.inventory.sale;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private UUID saleId;
    private UUID catchId;
    private String seafoodType;
    private BigDecimal kilos; // Ensure this field is present
    private LocalDate saleDate;
/*    private Float kilosFromCatch; // Total kilos available in the catch
    private Float kilosSold; // Kilos sold in the sale*/
}
