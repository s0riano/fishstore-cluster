package com.seafood.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResponsDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private int phoneNumber;
    private String email;
    private LocalDateTime accountCreated;
    private LocalDateTime lastUpdated;
}
