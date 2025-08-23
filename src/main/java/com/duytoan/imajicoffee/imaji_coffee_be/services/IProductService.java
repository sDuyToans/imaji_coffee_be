package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.ProductDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.Product;

import java.util.List;


public interface IProductService {
    /**
     *
     * @return all Product objects
     */
    public abstract List<ProductDto> getProducts();
}
