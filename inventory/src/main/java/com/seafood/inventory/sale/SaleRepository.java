package com.seafood.inventory.sale;

import com.seafood.inventory.enums.SeafoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByCatchEntityCatchId(Long catchId);

    List<Sale> findByCatchEntitySellerId(Long sellerId);

    @Query("SELECT SUM(s.kilos) FROM Sale s WHERE s.catchEntity.sellerId = :sellerId AND s.catchEntity.seafoodType = :seafoodType")
    Float sumKilosBySellerIdAndSeafoodType(@Param("sellerId") Long sellerId, @Param("seafoodType") SeafoodType seafoodType);

    //List<Sale> findByCatchId(Long catchId);
}
