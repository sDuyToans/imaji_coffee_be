package com.duytoan.imajicoffee.imaji_coffee_be.services.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Ship;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ShipMethodDto;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.ShipRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.IShipMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipMethodServiceImpl implements IShipMethodService {

    private final ShipRepository shipRepository;

    @Override
    public List<ShipMethodDto> getShipMethods() {
        return shipRepository.findAllByIsActiveTrue()
                .stream().map(this::mapToShipMethodDto).toList();
    }

    private ShipMethodDto mapToShipMethodDto(Ship shipMethod){
        ShipMethodDto shipMethodDto = new ShipMethodDto();
        BeanUtils.copyProperties(shipMethod, shipMethodDto);
        return shipMethodDto;
    }
}
