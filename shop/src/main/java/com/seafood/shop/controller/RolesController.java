package com.seafood.shop.controller;


import com.seafood.shop.dto.ShopRoleDTO;
import com.seafood.shop.entity.ShopRole;
import com.seafood.shop.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesService rolesService;

    @Autowired
    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/{userId}")
    public List<ShopRoleDTO> getUserRoles(@PathVariable UUID userId) {
        return rolesService.getUserRoleDTOs(userId);
    }
}
