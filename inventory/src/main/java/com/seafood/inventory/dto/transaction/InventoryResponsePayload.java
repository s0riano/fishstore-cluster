package com.seafood.inventory.dto.transaction;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private UUID transactionId; //might need to be String?
    private boolean isAvailable;
}
