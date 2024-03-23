package com.seafood.inventory.staticInventory.preorder.failed;

import com.seafood.inventory.staticInventory.preorder.PreorderItems;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "failed")
public class FailedSale {

    @Id
    @NotNull
    private UUID saleId;

    @NotNull
    private UUID transactionId;

    @NotNull
    private UUID inventoryId;

    private List<PreorderItems> soldItems;

}
