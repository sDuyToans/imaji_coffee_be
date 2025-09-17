package com.duytoan.imajicoffee.imaji_coffee_be.repository.common;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.common.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByStartDateAfterOrderByStartDateDesc(Instant instant, Pageable pageable);
    Page<Event> findByStartDateBeforeOrderByStartDateAsc(Instant instant, Pageable pageable);
}
