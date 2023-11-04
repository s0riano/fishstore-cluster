package com.fishtore.transaction.dto.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private Long transactionId;
    private boolean isAvailable;
}
