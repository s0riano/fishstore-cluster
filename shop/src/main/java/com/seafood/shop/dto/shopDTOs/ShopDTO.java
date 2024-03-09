package com.seafood.shop.dto.shopDTOs;

import com.seafood.shop.dto.user.ShopOwnerDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    @NotBlank
    private UUID id;
    private String name;
    private String description;
    private Set<ShopOwnerDTO> owners;
}
