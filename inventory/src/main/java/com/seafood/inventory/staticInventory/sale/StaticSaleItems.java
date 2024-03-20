package com.seafood.inventory.staticInventory.sale;

import com.seafood.inventory.entities.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticSaleItems {
    private List<SaleItem> items;

    @Slf4j
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleItem {
        private SeafoodType seafoodType;
        private double kilos;
    }
}
