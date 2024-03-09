package com.seafood.shop.legacy.service;

import com.seafood.shop.dto.shopDTOs.ShopRoleDTO;
import com.seafood.shop.legacy.entity.ShopRole;

import java.util.List;
import java.util.UUID;

public interface RolesService {
    List<ShopRole> getSellersRolesByUUID(UUID sellerId);
    List<ShopRoleDTO> getUserRoleDTOs(UUID userId);
}
