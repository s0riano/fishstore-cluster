package com.seafood.shop.dto.shopDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateShopDTO {
    private String shopName;
    private String description;
    private String location;
    private String locationDescription;
    private String contactInfo;
}
