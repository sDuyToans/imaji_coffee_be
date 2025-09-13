package com.duytoan.imajicoffee.imaji_coffee_be.repository;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
