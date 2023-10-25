package com.seafood.inventory.sellercatch;

import com.seafood.inventory.enums.SeafoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Catch getCatchById(@PathVariable Long id) {
        return catchService.getCatchById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteCatch(@PathVariable Long id) {
        catchService.deleteCatch(id);
    }

    @GetMapping("/sellers/{sellerId}")
    public List<Catch> getCatchesBySellerId(@PathVariable Long sellerId) {
        return catchService.getCatchesBySellerId(sellerId);
    }

    @PostMapping
    public Catch saveCatch(@RequestBody Catch catchData) {
        return catchService.saveCatch(catchData);
    }

    @PostMapping("/{sellerId}")
    public Catch saveCatchForSeller(@PathVariable Long sellerId, @RequestBody Catch catchData) {
        // You can either set the sellerId to the catchData or use it for validation purposes
        catchData.setSellerId(sellerId);
        return catchService.saveCatch(catchData);
    }

    /*@GetMapping("/sellers/{sellerId}/inventory")
    public Map<SeafoodType, Integer> getInventoryBySellerId(@PathVariable Long sellerId) {
        return catchService.getInventoryBySellerId(sellerId);
    }*/

}