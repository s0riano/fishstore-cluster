package com.seafood.inventory.entities.dto;

import com.seafood.inventory.entities.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatchDTO {
    private UUID catchId;
    private SeafoodType seafoodType;
    private BigDecimal totalKilos;
    private Date dateCaught; //might be wrong date type
}
