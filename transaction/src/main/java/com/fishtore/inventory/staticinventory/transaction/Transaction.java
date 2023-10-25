package com.fishtore.inventory.staticinventory.transaction;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "seafood_type", nullable = false)
    private List<TransactionItem> items;

    @Column(name = "kilos", nullable = false)
    private BigDecimal kilos;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "creation_timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationTimestamp; //might change this name, not clear enough

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status = TransactionStatus.PENDING; //default status

    @Column(name = "pickup_timestamp", nullable = true)
    private LocalDateTime pickupTimestamp; //when turned complete/picked up

    @Column(name = "notes")
    private String notes;
}
