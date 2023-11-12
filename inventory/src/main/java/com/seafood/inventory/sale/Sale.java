package com.seafood.inventory.sale;

import com.seafood.inventory.sellercatch.Catch;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/*
                   - not a transaction -
       here, we are storing the sale inside the catch
       when rabbit gets a transaction message for a sale,
       it will check for availability, and if available,
       it will create a sale. This sale, as said above,
       will be stored inside/linked to the catch of the batch
   */
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
