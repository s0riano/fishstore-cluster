package dto.staticInventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForDateDTO {
    LocalDateTime sellingDate;
    //add location?
    List<TotalStockDTO> stock;
}
