package dto.staticInventory;

import enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificItemStock {
    SeafoodType seafoodType;
    double remainingKilos; //has to be calculated in backend
    //implement later a list of sales?
}
