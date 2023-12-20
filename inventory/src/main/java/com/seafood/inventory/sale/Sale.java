package com.seafood.inventory.sale;

import com.seafood.inventory.sellercatch.Catch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "sale_id")
    private UUID saleId;

    @NotNull
    @Column(name = "transaction_id")
    private UUID transactionId;

    @NotNull
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "catch_id", insertable = false, updatable = false)
    private Catch catchEntity;

    @NotNull
    //@Transient
    @Column(name = "catch_id")
    private UUID catchId;

    @NotNull
    @Column(name = "kilos", nullable = false)
    private BigDecimal kilos;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    public UUID getCatchId() {
        return (catchEntity != null) ? catchEntity.getCatchId() : null;
    }
}
