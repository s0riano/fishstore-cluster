package com.seafood.inventory.entities.dto;

import com.seafood.inventory.entities.enums.SeafoodType;
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

