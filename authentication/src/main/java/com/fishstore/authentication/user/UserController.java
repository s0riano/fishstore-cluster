package com.fishstore.authentication.user;

import com.fishstore.authentication.config.JwtGeneratorInterface;
import com.fishstore.authentication.dto.LoginDTO;
import com.fishstore.authentication.dto.ShopRoleDTO;
import com.fishstore.authentication.exception.UserNotFoundException;
import com.fishstore.authentication.service.UserService;
import com.fishstore.authentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtGeneratorInterface jwtGenerator;

    @Autowired
    public UserController(UserService userService, JwtGeneratorInterface jwtGenerator){
        this.userService=userService;
        this.jwtGenerator=jwtGenerator;
    }


    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody LoginDTO newUser){
        try{
            userService.saveNewUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            if(loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
                throw new UserNotFoundException("UserName or Password is Empty");
            }
            User userData = userService.getUserEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
            if(userData == null){
                throw new UserNotFoundException("UserName or Password is Invalid");
            }
            Set<ShopRoleDTO> roles = userService.getUserRoles(userData.getId());

            return new ResponseEntity<>(jwtGenerator.generateToken(userData, roles), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
