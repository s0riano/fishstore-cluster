package dto;

import enums.SeafoodType;
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


