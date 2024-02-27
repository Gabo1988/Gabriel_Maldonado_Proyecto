package com.prueba.tec.repository;

import com.prueba.tec.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p " +
            "WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND :date BETWEEN p.startDate AND p.endDate")
    List<Price> selectPricesByBrandIdAndProductIdAndDate(
            @Param("brandId") String brandId,
            @Param("productId") String productId,
            @Param("date") Timestamp date
    );
}
