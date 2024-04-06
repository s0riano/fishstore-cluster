package com.fishtore.price.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriceDTO {
    @NotNull
    private String id;
    private String UpdatedBy;
    @NotNull
    private List<PriceEntryDTO> prices;
}
