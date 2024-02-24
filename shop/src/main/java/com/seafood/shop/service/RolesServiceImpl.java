package com.seafood.shop.service;

import com.seafood.shop.dto.ShopRoleDTO;
import com.seafood.shop.entity.ShopRole;
import com.seafood.shop.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<ShopRole> getSellersRolesByUUID(UUID sellerId) {
        return rolesRepository.findRolesByUserId(sellerId);
    }

    @Override
    public List<ShopRoleDTO> getUserRoleDTOs(UUID userId) {
        // Fetch roles associated with the user
        List<ShopRole> roles = rolesRepository.findRolesByUserId(userId);

        // Convert each ShopRole to ShopRoleDTO
        return roles.stream()
                .map(role -> new ShopRoleDTO(role.getShop().getId(), role.getRole()))
                .collect(Collectors.toList());
    }

}