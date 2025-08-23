package com.duytoan.imajicoffee.imaji_coffee_be.repository;


import com.duytoan.imajicoffee.imaji_coffee_be.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
