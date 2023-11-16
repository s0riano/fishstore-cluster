package com.seafood.inventory.dto.transaction;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private String transactionId;
    private boolean isAvailable;
}
