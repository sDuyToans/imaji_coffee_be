package com.duytoan.imajicoffee.imaji_coffee_be.repository.product;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query("SELECT p FROM Promo p JOIN p.promoProducts pp " +
            "WHERE pp.product.productId = :productId " +
            "AND p.isActive = true " +
            "AND p.startAt <= CURRENT_TIMESTAMP  " +
            "AND p.endAt >= CURRENT_TIMESTAMP ")
    List<Promo> findAvailablePromosByProductId(@Param("productId") Long productId);


    @Query("SELECT p FROM Promo p " +
            "WHERE p.isActive = true " +
            "AND p.startAt <= CURRENT_TIMESTAMP " +
            "AND p.endAt >= CURRENT_TIMESTAMP " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM PromoProduct pp " +
            "   WHERE pp.promo = p AND pp.product.productId = :productId)")
    List<Promo> findUnavailablePromosByProductId(@Param("productId") Long productId);
}
