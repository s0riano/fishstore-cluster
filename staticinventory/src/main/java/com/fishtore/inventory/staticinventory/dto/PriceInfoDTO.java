package com.fishtore.inventory.staticinventory.dto;

import com.fishstore.shared.dto.SeafoodType;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfoDTO {
    private SeafoodType seafoodType;
    private BigDecimal pricePerKilo;
}

