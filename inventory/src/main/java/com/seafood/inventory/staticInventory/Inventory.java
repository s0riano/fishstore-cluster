package com.seafood.inventory.staticInventory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventory")
public class Inventory {

    // inventory will be related to one single day.
    // So this inventory, will be the inventory for a single day
    // Make a date for when it was created. Might be nice for users to backtrack history
    //TODO: Make some check or something for inventory only being able to exist if in relation to a existing shore-date of the store


    @Id
    private String inventoryId; // try to refactor to String
    @NotNull
    private LocalDateTime  sellingDate; //can be morphed or edited or connected to some calendar functionality? like calendar id?
    @NotNull
    private UUID shopId;
    @NotNull
    private List<Stock> stock;
    private LocalDateTime lastUpdated;
    private List<UUID> listOfSaleIds; //maybe map with transactionId later
    private List<String> adjustedBy; //This will be a list of who have touched/adjusted the inventory


    public UUID getInventoryId() {
        return UUID.fromString(this.inventoryId);
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId.toString();
    }
}
