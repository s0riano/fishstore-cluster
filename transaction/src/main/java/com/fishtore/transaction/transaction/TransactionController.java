package com.fishtore.transaction.transaction;


import dto.PickupRequestDTO;
import dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/placeOrder") //handle if id's are not uuid
    public ResponseEntity<String> placeOrder(@RequestBody TransactionDTO transactionDTO) {
        String response = transactionService.processOrderPlacement(transactionDTO);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("/pickup")
    public ResponseEntity<?> pickupTransaction(@RequestBody PickupRequestDTO pickupRequest) {
        try {
            transactionService.updatePickupTimestamp(pickupRequest.getTransactionId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
