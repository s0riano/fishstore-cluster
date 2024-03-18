package com.seafood.inventory.sellercatch;


import com.seafood.inventory.entities.enums.SeafoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface CatchRepository extends JpaRepository<Catch, UUID> {

    @Query("SELECT SUM(c.kilos) FROM Catch c WHERE c.seafoodType = :type")
    Float sumBySeafoodType(SeafoodType type);

    List<Catch> findByShopId(UUID shopId);

    @Query("SELECT c.seafoodType, SUM(c.kilos) FROM Catch c WHERE c.shopId = :shopId GROUP BY c.seafoodType")
    List<Object[]> getInventoryForShop(@Param("shopId") UUID shopId);

    @Query("SELECT SUM(c.kilos) FROM Catch c WHERE c.shopId = :shopId AND c.seafoodType = :seafoodType")
    BigDecimal sumKilosByShopIdAndSeafoodType(@Param("shopId") UUID shopId, @Param("seafoodType") SeafoodType seafoodType);

    @Query("SELECT DISTINCT c.shopId FROM Catch c")
    List<UUID> findAllDistinctShopIds();

    @Query("SELECT c FROM Catch c WHERE c.shopId = :shopId AND c.seafoodType = :seafoodType ORDER BY c.expiryDate ASC")
    List<Catch> findByShopIdAndSeafoodTypeSortedByExpiryDate(@Param("shopId") UUID shopId, @Param("seafoodType") SeafoodType seafoodType);
}
