package com.seafood.inventory.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByCatchEntityCatchId(Long catchId);

    List<Sale> findByCatchEntitySellerId(Long sellerId);

    //List<Sale> findByCatchId(Long catchId);
}
