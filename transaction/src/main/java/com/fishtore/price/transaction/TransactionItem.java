package com.fishtore.price.transaction;

import com.fishstore.shared.dto.SeafoodType;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItem {
    private SeafoodType seafoodType;

    @Setter(AccessLevel.NONE) // Prevent Lombok from generating a setter for this field
    private BigDecimal kilos = BigDecimal.ZERO;

    @Setter(AccessLevel.NONE) // Prevent Lombok from generating a setter for this field
    private BigDecimal pricePerKilo = BigDecimal.ZERO;

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos != null ? kilos : BigDecimal.ZERO;
    }

    public void setPricePerKilo(BigDecimal pricePerKilo) {
        this.pricePerKilo = pricePerKilo != null ? pricePerKilo : BigDecimal.ZERO;
    }
}
