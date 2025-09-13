package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ShipMethodDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.ShipMethodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ship-methods")
@RequiredArgsConstructor
public class ShipMethodController {
    private final ShipMethodServiceImpl shipMethodService;

    @GetMapping
    public ResponseEntity<List<ShipMethodDto>> getAllShipMethods() {
        List<ShipMethodDto> shipMethodDtoList = shipMethodService.getShipMethods();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shipMethodDtoList);
    }
}
