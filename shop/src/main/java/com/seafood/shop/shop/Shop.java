package com.seafood.shop.shop;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seafood.shop.legacy.entity.ShopRole;
import com.seafood.shop.openingHours.OpeningHours;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @NotNull
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(
            mappedBy = "shop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<ShopRole> owners = new HashSet<>();

    @Column(name = "shop_name", nullable = false)
    @Size(max = 20)
    private String shopName;

    @Column(name = "description")
    @Size(max = 200)
    private String description;

    @Column(name = "location")
    private String location; // Consider the appropriate data type , will have adress for now

    @Column(name = "location_description")
    @Size(max = 200)
    private String locationDescription;

    @Column(name = "contact_info")
    private String contactInfo;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OpeningHours> openingHours = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
