package com.seafood.shop.develop;

import com.seafood.shop.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DemoController { //to test functionality with the authentication header etc

    @GetMapping("/demo")
    public ResponseEntity<String> demoEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("You are trying to log in as: " + username);

        return ResponseEntity.ok("You are logged in as: " + username);
    }

    @GetMapping("/shop")
    public ResponseEntity<String> demoPostToStore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UUID storeId = userDetails.getShopId();

            log.info("Store ID: {}", storeId);
            return ResponseEntity.ok("Your Store ID is: " + storeId);
        } else {
            log.info("Authentication principal is not an instance of CustomUserDetails: {}", authentication);
            return ResponseEntity.badRequest().body("Store ID not found");
        }
    }
}

