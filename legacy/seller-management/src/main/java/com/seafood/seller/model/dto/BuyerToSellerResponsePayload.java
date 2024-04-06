package com.seafood.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerToSellerResponsePayload {
    private String sellerId;
    private boolean buyerIsDeactivated; //1/true means OK, it means that he successfully was deactivated
}
