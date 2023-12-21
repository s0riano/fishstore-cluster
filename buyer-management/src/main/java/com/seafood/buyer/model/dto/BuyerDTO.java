package com.seafood.buyer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDTO {

    @NotBlank
    @Size(min = 15)
    private String firstName;

    @NotBlank
    @Size(min = 20)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private int phoneNumber;

    @NotBlank
    @Size(min = 8) // You can specify a minimum password length
    private String password;
}
