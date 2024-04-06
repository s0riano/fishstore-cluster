package com.seafood.buyer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerToSellerRequestDTO {
    private UUID sellerId;
}
