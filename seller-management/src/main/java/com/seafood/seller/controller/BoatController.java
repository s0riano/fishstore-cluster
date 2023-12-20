/*
package com.seafood.seller.controller;

import com.seafood.seller.model.entity.Boat;
import com.seafood.seller.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boats")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    // Get all boats
    @GetMapping
    public List<Boat> getAllBoats() {
        return boatService.getAllBoats();
    }

    // Get a boat by ID
    @GetMapping("/{id}")
    public ResponseEntity<Boat> getBoatById(@PathVariable Long id) {
        Optional<Boat> boat = boatService.getBoatById(id);
        return boat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get a boat by registration mark
    @GetMapping("/registration/{registrationMark}")
    public ResponseEntity<Boat> getBoatByRegistrationMark(@PathVariable String registrationMark) {
        Optional<Boat> boat = boatService.getBoatByRegistrationMark(registrationMark);
        return boat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new boat
    @PostMapping
    public Boat addBoat(@RequestBody Boat boat) {
        return boatService.saveBoat(boat);
    }

    // Update a boat
    @PutMapping("/{id}")
    public ResponseEntity<Boat> updateBoat(@PathVariable Long id, @RequestBody Boat boatDetails) {
        Optional<Boat> boat = boatService.getBoatById(id);
        if (boat.isPresent()) {
            Boat updatedBoat = boat.get();
            updatedBoat.setRegistrationMark(boatDetails.getRegistrationMark());
            updatedBoat.setName(boatDetails.getName());
            boatService.saveBoat(updatedBoat);
            return ResponseEntity.ok(updatedBoat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a boat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoat(@PathVariable Long id) {
        if (boatService.getBoatById(id).isPresent()) {
            boatService.deleteBoat(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
*/
