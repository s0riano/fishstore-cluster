package com.fishtore.transaction.staticinventory.transaction;

import com.fishtore.transaction.staticinventory.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody TransactionDTO transactionDTO) {
        String response = transactionService.processOrderPlacement(transactionDTO);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

}
