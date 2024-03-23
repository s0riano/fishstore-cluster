package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDTO {
    private String shopId; // its stored as a string in the Price API instead of UUID
    private List<PriceEntryDTO> prices;
}
