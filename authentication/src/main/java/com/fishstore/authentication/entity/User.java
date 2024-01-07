package com.fishstore.authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private UUID id; //id should match user id in seller/buyer service

    private String email;    // Unique identifier for login
    private String password; // Encrypted password

    // other fields and methods
}
