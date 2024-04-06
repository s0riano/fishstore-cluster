package com.fishtore.price.operations.updateprice;

import com.fishtore.price.dto.UpdatePriceDTO;
import com.fishtore.price.price.Price;
import com.fishtore.price.price.PriceService;
import dto.preorder.PreOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/price")
public class UpdatePriceController {

    private final PriceService priceService;

    public UpdatePriceController(PriceService priceService) {
        this.priceService = priceService;
    }
    @PostMapping("/update") //handle if id's are not uuid
    public ResponseEntity<?> placeOrder(@RequestBody UpdatePriceDTO dto) {
        log.info("Received PriceUpdate for store: {}", dto);
        try {
            Price updatedPrice = priceService.updatePrices(dto);
            return ResponseEntity.ok(updatedPrice);
        } catch (RuntimeException ex) {
            log.error("Error updating price: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
