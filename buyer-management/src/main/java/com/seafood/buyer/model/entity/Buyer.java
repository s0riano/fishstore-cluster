package com.seafood.buyer.model.entity;

import com.seafood.buyer.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "buyer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer{

    @Id
    @NotNull
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private UUID id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "VARCHAR(255)"
    )
    @Size(max = 15)
    private String firstName;

    @Column(
            name = "last_name"
    )
    @Size(max = 40)
    private String lastName;

    @NotNull
    @Column(
            name = "phone_number",
            nullable = false
    ) //https://nrop.no/api_doc.html
    private int phoneNumber;

    @Email
    @NotNull
    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "VARCHAR(50)",
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "VARCHAR(255)"
    )
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "account_created")
    private LocalDateTime accountCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        accountCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}

