package dto.staticInventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForSpecificShopDTO {
    //@NotNull
    UUID shopId;
    List<InventoryForDateDTO> inventoryForDate;
}

//TODo: add some calculations for when the total kilos