package dto.staticInventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalStockDTO { //can this file be dropped?
    List<SpecificItemStock> totalStock; //all different seafoods for that day
}
