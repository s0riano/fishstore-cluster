package com.seafood.inventory.entities.enums;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreOrderTransactionDTO {
    @NotNull
    private UUID shopId; //can this be droppen
    @NotNull
    private UUID transactionId;
    @NotNull
    private UUID inventoryId;
    @NotNull
    private List<TransactionItemDTO> items;

    /*
        might need to add some functionality for date.
        it can be linked to the inventory date however... anyway it is a concern for later x)

        local date when the order was sendt? can also be made in the backend
    */
}
