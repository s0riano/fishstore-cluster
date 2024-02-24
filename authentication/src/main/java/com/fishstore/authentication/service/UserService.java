package com.fishstore.authentication.service;


import com.fishstore.authentication.dto.LoginDTO;
import com.fishstore.authentication.dto.ShopRoleDTO;
import com.fishstore.authentication.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public interface UserService {

    void saveUser(User user);
    void saveNewUser(LoginDTO user);

    Optional<User> findByEmail(String email);

    User getUserEmailAndPassword(String email, String password); //throws UserNotFoundException;

    Set<ShopRoleDTO> getUserRoles(UUID id);
}

