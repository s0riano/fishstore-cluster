package com.seafood.shop.dto.shopDTOs;

import com.seafood.shop.legacy.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopRoleDTO {
    private UUID shopId;
    private Role role;
}
