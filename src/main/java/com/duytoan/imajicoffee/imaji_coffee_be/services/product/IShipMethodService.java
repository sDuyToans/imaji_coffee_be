package com.duytoan.imajicoffee.imaji_coffee_be.services.product;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ShipMethodDto;

import java.util.List;

public interface IShipMethodService {
    public abstract List<ShipMethodDto> getShipMethods();
}
