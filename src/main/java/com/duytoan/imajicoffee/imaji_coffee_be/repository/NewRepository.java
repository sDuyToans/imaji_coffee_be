package com.duytoan.imajicoffee.imaji_coffee_be.repository;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewRepository extends JpaRepository<News, Long> {
}
