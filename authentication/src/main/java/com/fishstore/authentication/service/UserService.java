package com.fishstore.authentication.service;


import com.fishstore.authentication.client.ShopServiceClient;
import com.fishstore.authentication.enums.ShopRole;
import com.fishstore.authentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private ShopServiceClient shopServiceClient;

    public User createUserWithRoles(UUID userId) {
        Set<ShopRole> roles = shopServiceClient.getRolesForUser(userId);
        // Fetch other user details and create a User object
        User user = new User();
        // Set roles and other details to the user
        user.setShopRoles(roles);
        return user;
    }
}

