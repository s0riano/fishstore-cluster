package com.seafood.shop.dto.shopDTOs;

import com.seafood.shop.enums.SeafoodType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopLandingPageDTO {

    @NotBlank
    private UUID id; //use with react router for sending url-links to shops
    @NotBlank
    private String name;
    private String location; //new
    private String description;//new
    @NotBlank
    private List<SeafoodType> items;
}
