package com.fishtore.transaction.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private Long transactionId;
    private boolean isAvailable;
}
