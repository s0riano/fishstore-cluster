package com.seafood.inventory.sale;

import com.seafood.inventory.sellercatch.Catch;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catch_id", nullable = false)  // Indicates the foreign key column
    private Catch catchEntity;

    @Transient
    private Long catchId;

    @Column(name = "kilos", nullable = false)
    private float kilos;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    public Long getCatchId() {
        return (catchEntity != null) ? catchEntity.getCatchId() : null;
    }
}
