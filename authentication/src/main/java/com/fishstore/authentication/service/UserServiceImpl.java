package com.fishstore.authentication.service;

//import com.stackroute.AuthenticatorService.model.User;
//import com.stackroute.AuthenticatorService.repository.UserRepository;
import com.fishstore.authentication.components.ExternalRolesApiComponent;
import com.fishstore.authentication.dto.LoginDTO;
import com.fishstore.authentication.dto.ShopRoleDTO;
import com.fishstore.authentication.exception.UserNotFoundException;
import com.fishstore.authentication.user.UserRepository;
import com.fishstore.authentication.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ExternalRolesApiComponent externalRolesApiComponent;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ExternalRolesApiComponent externalRolesApiComponent){
        this.userRepository=userRepository;
        this.externalRolesApiComponent = externalRolesApiComponent;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveNewUser(LoginDTO userDTO) { //do i need a register
        //create a UUID
        //UUID id = UUID.randomUUID();
        //User user =  new User(id,userDTO.getEmail(), userDTO.getPassword(), false);
        //userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserEmailAndPassword(String email, String password) {// throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if(user == null){
            log.error("throw new UserNotFoundException(Invalid id and password);");
            throw new UserNotFoundException("Invalid id and password");
        }
        return user;
    }

    @Override
    public Set<ShopRoleDTO> getUserRoles(UUID id) {
        return externalRolesApiComponent.getListOfRoles(id);
    }

    @Cacheable(value = "userRoles", key = "#userId")
    public Set<ShopRoleDTO> getUserRolesAndCache(UUID userId) {
        return externalRolesApiComponent.getListOfRoles(userId);
    }
}
