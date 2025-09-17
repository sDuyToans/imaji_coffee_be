package com.duytoan.imajicoffee.imaji_coffee_be.services.product;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductPromosDto;

public interface IPromoService {
    public abstract ProductPromosDto getPromosForProduct(Long productId);
}
