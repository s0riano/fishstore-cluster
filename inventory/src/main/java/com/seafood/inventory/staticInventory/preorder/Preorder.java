package com.seafood.inventory.staticInventory.preorder;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "preorder")
public class Preorder {

    @Id
    @NotNull
    private UUID preorderId;

    @NotNull
    private UUID transactionId;

    @NotNull
    private UUID inventoryId;

    @NotNull
    private List<PreorderItems> soldItems;

    @NotNull
    private LocalDate saleDate;
}


//TODO: add if its picked up? bool.