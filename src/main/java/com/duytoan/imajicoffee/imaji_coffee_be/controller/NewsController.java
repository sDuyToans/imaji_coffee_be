package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.NewDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.NewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewServiceImpl newServiceImpl;

    @GetMapping
    public ResponseEntity<List<NewDto>> getTopNewsByCreatedDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        List<NewDto> newDtos = newServiceImpl.getPageNews(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newDtos);
    }
}
