package com.fishtore.transaction.transaction.failures;

import com.fishtore.transaction.transaction.TransactionStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "failed_transactions")
public class FailedTransaction {
    @Id
    private String id;
    private String transactionId;
    private TransactionStatus status;
    private LocalDateTime failedAt;
    private String errorMessage;
}
