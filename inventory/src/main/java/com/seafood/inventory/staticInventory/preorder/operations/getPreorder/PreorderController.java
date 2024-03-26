package com.seafood.inventory.staticInventory.preorder.operations.getPreorder;

import com.seafood.inventory.staticInventory.preorder.Preorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/preorder")
public class PreorderController {

    private final PreorderHandler handler;

    @Autowired
    public PreorderController(PreorderHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/{preorderId}")
    public ResponseEntity<Preorder> getPreorder(@PathVariable UUID preorderId) {
        Optional<Preorder> preorder = handler.getPreorderById(preorderId);
        return preorder.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}