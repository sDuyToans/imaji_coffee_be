package com.duytoan.imajicoffee.imaji_coffee_be.repository.product;


import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * Product repository
 * @author duytoan
 * @since 10/2025
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Filter product by category and pageable
     * @param category
     * @param pageable
     * @return products page
     */
    Page<Product> findByCategory(String category, Pageable pageable);

    /**
     * Filter product by category, name, and price
     * @param category
     * @param search
     * @param maxPrice
     * @param pageable
     * @return products page
     */
    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> searchProducts(
            @Param("category") String category,
            @Param("search") String search,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    /**
     * Filter related products by category and exclude current product by using product id
     * @param category
     * @param excludeId
     * @param pageable
     * @return products page
     */
    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:excludeId IS NULL OR p.productId <> :excludeId)")
    Page<Product> getRelatedProducts(@Param("category") String category, @Param("excludeId") Long excludeId, Pageable pageable);
}
