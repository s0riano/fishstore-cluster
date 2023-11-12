package com.seafood.inventory.dto;

import com.seafood.inventory.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntryDTO {
    SeafoodType seafoodType;
    private BigDecimal price;
}

