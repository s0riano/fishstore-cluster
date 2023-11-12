package com.fishtore.price.dto.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponsePayload {
    private Long transactionId;
    private boolean isAvailable;
}
