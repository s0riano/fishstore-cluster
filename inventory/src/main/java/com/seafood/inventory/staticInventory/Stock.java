package com.seafood.inventory.staticInventory;

import com.seafood.inventory.entities.enums.SeafoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    Double initialKilos;
    Double remainingKilos;
    SeafoodType seafoodType;


    public void deductKilos(Double kilosToDeduct) {
        // Check if the input kilosToDeduct and the current remainingKilos are not null
        if (kilosToDeduct != null && this.remainingKilos != null) {
            // Deduct kilos, ensuring the value does not fall below zero
            this.remainingKilos = Math.max(this.remainingKilos - kilosToDeduct, 0.0);
        }
    }
}
