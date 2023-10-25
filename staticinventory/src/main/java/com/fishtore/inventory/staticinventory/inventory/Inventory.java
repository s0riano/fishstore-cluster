package com.fishtore.inventory.staticinventory.inventory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Enumerated(EnumType.STRING) // This annotation is used to specify how the enum will be stored in the database
    @Column(name = "seafood_type", nullable = false)
    private SeafoodType seafoodType;

    @Column(name = "kilos", nullable = false)
    private BigDecimal kilos;

    @Column(name = "price_per_kilo", nullable = false)
    private BigDecimal pricePerKilo;

    @Column(name = "last_updated", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated;
}
