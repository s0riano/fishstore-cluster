package com.fishtore.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDTO {
    private Long sellerId;
    private List<PriceEntryDTO> prices;
}
