package com.fishtore.transaction.operations;

import com.fishtore.transaction.transaction.TransactionService;
import dto.preorder.PreOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class PreOrderControllerr {

    private final TransactionService transactionService;

    public PreOrderControllerr(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/preorder") //handle if id's are not uuid
    public ResponseEntity<?> placeOrder(@RequestBody PreOrderDTO preOrderDTO) {
        log.info("Received preOrderDTO: {}", preOrderDTO);
        String response = transactionService.processPreOrder(preOrderDTO);
        return ResponseEntity.accepted().body(response);
    }
}
