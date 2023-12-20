package com.seafood.inventory.sellercatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catches")
public class CatchController {

    @Autowired
    private CatchService catchService;

    @GetMapping
    public List<Catch> getAllCatches() {
        return catchService.getAllCatches();
    }

    @GetMapping("/{id}")
    public Catch getCatchById(@PathVariable UUID id) {
        return catchService.getCatchById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteCatch(@PathVariable UUID id) {
        catchService.deleteCatch(id);
    }

    @GetMapping("/sellers/{sellerId}")
    public List<Catch> getCatchesBySellerId(@PathVariable UUID sellerId) {
        return catchService.getCatchesBySellerId(sellerId);
    }

    @PostMapping
    public Catch saveCatch(@RequestBody Catch catchData) {
        return catchService.saveCatch(catchData);
    }

    @PostMapping("/{sellerId}")
    public Catch saveCatchForSeller(@PathVariable UUID sellerId, @RequestBody Catch catchData) {
        // You can either set the sellerId to the catchData or use it for validation purposes
        catchData.setShopId(sellerId);
        return catchService.saveCatch(catchData);
    }

    /*@GetMapping("/sellers/{sellerId}/inventory")
    public Map<SeafoodType, Integer> getInventoryBySellerId(@PathVariable Long sellerId) {
        return catchService.getInventoryBySellerId(sellerId);
    }*/

}