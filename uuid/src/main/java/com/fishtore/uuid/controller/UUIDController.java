package com.fishtore.uuid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/uuid")
public class UUIDController {

    @GetMapping("/generate")
    public ResponseEntity<String> generateUUID() {
        UUID uuid = UUID.randomUUID();
        return ResponseEntity.ok(uuid.toString());
    }
}
