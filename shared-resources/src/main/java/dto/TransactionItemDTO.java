package dto;

import enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItemDTO {
    private SeafoodType seafoodType;
    private BigDecimal kilos;
    private BigDecimal pricePerKilo;
}