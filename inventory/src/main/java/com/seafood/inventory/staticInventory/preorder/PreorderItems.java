package com.seafood.inventory.staticInventory.preorder;


import enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreorderItems {
    private List<Item> items;

    @Slf4j
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private SeafoodType seafoodType;
        private double kilos;
    }
}
