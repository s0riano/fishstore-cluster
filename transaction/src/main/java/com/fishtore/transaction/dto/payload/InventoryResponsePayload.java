package com.fishtore.transaction.dto.payload;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private UUID transactionId;
    private boolean isAvailable;
}
