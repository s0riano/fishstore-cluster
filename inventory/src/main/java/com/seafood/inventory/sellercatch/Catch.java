package com.seafood.inventory.sellercatch;

import com.seafood.inventory.entities.enums.SeafoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "catch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Catch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catch_id")
    private UUID catchId;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Enumerated(EnumType.STRING) // Storing the enum values as strings
    @Column(name = "seafood_type", nullable = false)
    private SeafoodType seafoodType;

    @Column(nullable = false)
    private BigDecimal kilos;

    @Column(name = "catch_date", nullable = false)
    private LocalDate dateCaught;

    /*@Column(name = "time_of_day", nullable = false)
    private LocalTime timeOfDay; // Using LocalTime for time_of_day*/

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;
}
