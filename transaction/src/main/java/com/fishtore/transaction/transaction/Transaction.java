package com.fishtore.transaction.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String transactionId;

    private Long sellerId;
    private Long buyerId;
    private List<TransactionItem> items;

    private BigDecimal kilos; //hmm
    private BigDecimal totalPrice;
    private LocalDateTime creationTimestamp; //might change this name, not clear enough
    private TransactionStatus status = TransactionStatus.PENDING; //default status
    private LocalDateTime pickupTimestamp; //when turned complete/picked up
    private String notes;

    public BigDecimal calculateTotalPrice() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getKilos().multiply(item.getPricePerKilo()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
    }
}