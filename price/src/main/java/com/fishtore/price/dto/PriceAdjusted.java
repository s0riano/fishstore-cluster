package com.fishtore.price.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjusted {
    String updatedBy;
    Date dateLastUpdated;
}
