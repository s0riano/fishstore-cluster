package com.fishtore.transaction.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private UUID transactionId; //was string, might change back since i do not like how the binary is visually displayed in mongo
    @NotNull
    private UUID shopId; //can drop this?
    @NotNull
    private UUID inventoryId;
    @NotNull
    private UUID buyerId; //might change this to some sort of object, so the seller can buy without having an id, but rather buy and just leave an alias or
    @NotNull
    private List<TransactionItem> items;

    private BigDecimal kilos; //hmm
    private BigDecimal totalPrice;

    private TransactionStatus status = TransactionStatus.PENDING; //default status
    private PriceStatus priceStatus = PriceStatus.NOT_CHECKED; //default status

    private LocalDateTime creationTimestamp; //might change this name, not clear enough
    private LocalDateTime pickupTimestamp; //when turned complete/picked up
    private String notes; //can be helpful later, either it will be for debugging or buyer notes

    //bool picked u


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