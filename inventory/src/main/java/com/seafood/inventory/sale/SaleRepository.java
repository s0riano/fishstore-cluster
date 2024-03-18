package com.seafood.inventory.sale;

import com.seafood.inventory.entities.enums.SeafoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    List<Sale> findByCatchEntityCatchId(UUID catchId);

    List<Sale> findByCatchEntityShopId(UUID shopId);

    @Query("SELECT SUM(s.kilos) FROM Sale s WHERE s.catchEntity.shopId = :shopId AND s.catchEntity.seafoodType = :seafoodType")
    BigDecimal sumKilosByShopIdAndSeafoodType(@Param("shopId") UUID shopId, @Param("seafoodType") SeafoodType seafoodType);

    @Query("SELECT SUM(s.kilos) FROM Sale s WHERE s.catchEntity.catchId = :catchId")
    BigDecimal sumKilosForCatch(@Param("catchId") UUID catchId);

    /*@Query("SELECT SUM(s.kilos) FROM Sale s WHERE s.catchEntity.catchId = :catchId")
    BigDecimal sumKilosForCatch(@Param("catchId") UUID catchId);*/
    //List<Sale> findByCatchId(Long catchId);
}
