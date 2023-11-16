package com.fishtore.transaction.transaction;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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

    private TransactionStatus status = TransactionStatus.PENDING; //default status
    private PriceStatus priceStatus = PriceStatus.NOT_CHECKED; //default status

    private LocalDateTime creationTimestamp; //might change this name, not clear enough
    private LocalDateTime pickupTimestamp; //when turned complete/picked up
    private String notes; //notes from the buyer

    public BigDecimal calculateTotalPrice() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> {
                    BigDecimal kilos = item.getKilos();
                    BigDecimal pricePerKilo = item.getPricePerKilo();
                    // Assuming you have a logger set up
                    log.info("Kilos: {}, PricePerKilo: {}", kilos, pricePerKilo);
                    if (kilos == null || pricePerKilo == null) {
                        log.warn("Null value detected for kilos or pricePerKilo");
                        kilos = kilos != null ? kilos : BigDecimal.ZERO;
                        pricePerKilo = pricePerKilo != null ? pricePerKilo : BigDecimal.ZERO;
                    }
                    return kilos.multiply(pricePerKilo);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
    }


}