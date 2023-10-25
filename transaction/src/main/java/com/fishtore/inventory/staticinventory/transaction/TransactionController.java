package com.fishtore.inventory.staticinventory.transaction;

import com.fishtore.inventory.staticinventory.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody TransactionDTO transactionDTO) {
        transactionService.requestItemAvailability(transactionDTO);
        return ResponseEntity.accepted().body("Order placed. Awaiting confirmation.");
    }
}
