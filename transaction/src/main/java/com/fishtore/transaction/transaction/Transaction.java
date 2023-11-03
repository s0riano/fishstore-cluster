package com.fishtore.transaction.staticinventory.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transaction")
public class Transaction {

    @Id
    private String transactionId;

    private Long sellerId;
    private Long buyerId;
    private List<TransactionItem> items;

    private BigDecimal kilos;
    private BigDecimal totalPrice;
    private LocalDateTime creationTimestamp; //might change this name, not clear enough
    private TransactionStatus status = TransactionStatus.PENDING; //default status
    private LocalDateTime pickupTimestamp; //when turned complete/picked up
    private String notes;
}