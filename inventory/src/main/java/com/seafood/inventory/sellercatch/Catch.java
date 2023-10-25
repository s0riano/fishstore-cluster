package com.seafood.inventory.sellercatch;

import com.seafood.inventory.enums.SeafoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime; // Importing LocalTime for time_of_day
import java.time.LocalDateTime;

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
    private Long catchId;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Enumerated(EnumType.STRING) // Storing the enum values as strings
    @Column(name = "seafood_type", nullable = false)
    private SeafoodType seafoodType;

    @Column(nullable = false)
    private Float kilos;

    @Column(name = "catch_date", nullable = false)
    private LocalDate dateCaught;

    /*@Column(name = "time_of_day", nullable = false)
    private LocalTime timeOfDay; // Using LocalTime for time_of_day*/

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;
}
