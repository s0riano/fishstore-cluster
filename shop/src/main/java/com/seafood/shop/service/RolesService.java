package com.seafood.shop.service;

import com.seafood.shop.dto.ShopRoleDTO;
import com.seafood.shop.entity.ShopRole;

import java.util.List;
import java.util.UUID;

public interface RolesService {
    List<ShopRole> getSellersRolesByUUID(UUID sellerId);
    List<ShopRoleDTO> getUserRoleDTOs(UUID userId);
}
