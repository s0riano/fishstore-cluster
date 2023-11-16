package com.seafood.inventory.sellercatch;


import com.seafood.inventory.enums.SeafoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatchRepository extends JpaRepository<Catch, Long> {

    @Query("SELECT SUM(c.kilos) FROM Catch c WHERE c.seafoodType = :type")
    Float sumBySeafoodType(SeafoodType type);

    List<Catch> findBySellerId(Long sellerId);

    @Query("SELECT c.seafoodType, SUM(c.kilos) FROM Catch c WHERE c.sellerId = :sellerId GROUP BY c.seafoodType")
    List<Object[]> getInventoryForSeller(@Param("sellerId") Long sellerId);

    @Query("SELECT SUM(c.kilos) FROM Catch c WHERE c.sellerId = :sellerId AND c.seafoodType = :seafoodType")
    Float sumKilosBySellerIdAndSeafoodType(@Param("sellerId") Long sellerId, @Param("seafoodType") SeafoodType seafoodType);
}
