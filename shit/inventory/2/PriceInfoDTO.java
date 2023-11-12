package com.fishstore.shared.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfoDTO {
    private SeafoodType seafoodType;
    private BigDecimal pricePerKilo;
}
