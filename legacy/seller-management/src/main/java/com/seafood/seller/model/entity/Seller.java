package com.seafood.seller.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller /*implements UserDetail*/{

    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private UUID id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "VARCHAR(255)"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "VARCHAR(255)"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "VARCHAR(255)",
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "VARCHAR(255)"
    )
    private String password;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
