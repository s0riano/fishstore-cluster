package com.fishstore.authentication.config;

import com.fishstore.authentication.dto.ShopRoleDTO;
import com.fishstore.authentication.user.User;

import java.util.Map;
import java.util.Set;

public interface JwtGeneratorInterface {
    Map<String, String> generateToken(User user, Set<ShopRoleDTO> roles);

}