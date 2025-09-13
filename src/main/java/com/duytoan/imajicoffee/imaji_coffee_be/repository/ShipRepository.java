package com.duytoan.imajicoffee.imaji_coffee_be.repository;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    public List<Ship> findAllByIsActiveTrue();
}
