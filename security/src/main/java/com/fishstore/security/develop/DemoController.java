package com.fishstore.security.develop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

