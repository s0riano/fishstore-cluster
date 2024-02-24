package com.seafood.shop.repository;


import com.seafood.shop.entity.ShopRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<ShopRole, UUID> {
    List<ShopRole> findRolesByUserId(UUID userId);
}

