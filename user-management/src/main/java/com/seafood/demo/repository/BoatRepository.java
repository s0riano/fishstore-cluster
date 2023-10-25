package com.seafood.demo.repository;

import com.seafood.demo.model.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoatRepository extends JpaRepository<Boat, Long> {

    Optional<Boat> findByRegistrationMark(String registrationMark);
}
