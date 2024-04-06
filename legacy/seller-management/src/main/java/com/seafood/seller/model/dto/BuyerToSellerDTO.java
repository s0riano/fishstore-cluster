package com.seafood.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerToSellerDTO { // the additional info needed from the buyer to become a buyer

    private UUID buyerId;
    private String city;
}
