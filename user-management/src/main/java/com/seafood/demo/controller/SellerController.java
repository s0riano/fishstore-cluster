package com.seafood.demo.controller;

import com.seafood.demo.model.entity.Seller;
import com.seafood.demo.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

}