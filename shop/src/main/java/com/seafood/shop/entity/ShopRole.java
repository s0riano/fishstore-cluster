package com.seafood.shop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seafood.shop.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "shop_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopRole { // should indeed be shopWorker or something, bad naming

    @Id
    @NotNull
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonManagedReference
    private Shop shop;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "role")
    @Enumerated(EnumType.STRING) //move this to a RBAC if time/remember
    private Role role;

}
