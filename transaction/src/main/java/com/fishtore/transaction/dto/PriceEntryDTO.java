package com.fishtore.transaction.dto;

import com.fishstore.shared.dto.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntryDTO {
    SeafoodType seafoodType;
    private BigDecimal price; //this is per kilo
}


