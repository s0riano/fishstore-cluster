package com.fishstore.shared.dto.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private String transactionId;
    private boolean isAvailable;
}
