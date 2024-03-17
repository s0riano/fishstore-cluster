package com.seafood.shop.openingHours;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seafood.shop.legacy.entity.ShopRole;
import com.seafood.shop.shop.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "opening_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    @Column(name = "date")
    private LocalDate date; // Represents the specific date

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "notes", length = 200)
    private String notes; // Any special notes for the day

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id") // foreign key column in 'opening_hours' table
    private Shop shop;
}
