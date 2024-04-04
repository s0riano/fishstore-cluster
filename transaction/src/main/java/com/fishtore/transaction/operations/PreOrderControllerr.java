package com.fishtore.transaction.operations;

import com.fishtore.transaction.transaction.TransactionService;
import dto.preorder.PreOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class PreOrderControllerr {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/preorder") //handle if id's are not uuid
    public ResponseEntity<?> placeOrder(@RequestBody PreOrderDTO preOrderDTO) {
        String response = transactionService.processPreOrder(preOrderDTO);
        return ResponseEntity.accepted().body(response);
    }
}
