package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.EventDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDto>> upcomingEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        List<EventDto> upcomingEvents = eventService.getPageUpComingEvent(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(upcomingEvents);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<EventDto>> closedEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        List<EventDto> closedEvents = eventService.getPageClosedEvent(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(closedEvents);
    }
}
