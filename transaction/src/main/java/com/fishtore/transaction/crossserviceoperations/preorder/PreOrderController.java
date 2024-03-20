package com.fishtore.transaction.crossserviceoperations.preorder;

import com.fishtore.transaction.dto.preorder.PreOrderTransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction/placeOrder")
public class PreOrderController {

    private final PreOrderHandler preOrderHandler;

    public PreOrderController(PreOrderHandler preOrderHandler) {
        this.preOrderHandler = preOrderHandler;
    }


    @PostMapping("/preorder")
    public ResponseEntity<String> placeOrder(@RequestBody PreOrderTransactionDTO dto) {
        preOrderHandler.handle(dto);
        return null;
    }
}
