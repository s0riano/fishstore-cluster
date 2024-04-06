package com.seafood.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerToSellerRequestDTO {
    private UUID sellerId;
}
