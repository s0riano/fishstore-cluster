package com.seafood.inventory.sale;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long saleId;
    private Long catchId;
    private String seafoodType;
    private Float kilos; // Ensure this field is present
    private LocalDate saleDate;
/*    private Float kilosFromCatch; // Total kilos available in the catch
    private Float kilosSold; // Kilos sold in the sale*/
}
