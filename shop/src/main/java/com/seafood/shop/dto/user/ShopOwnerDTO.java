package com.seafood.shop.dto.user;

import com.seafood.shop.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOwnerDTO {

    @NotBlank
    private UUID userId;
    @NotBlank
    private Role role;
}