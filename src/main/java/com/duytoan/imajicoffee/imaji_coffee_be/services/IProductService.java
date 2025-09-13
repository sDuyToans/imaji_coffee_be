package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductPageResponse;

import java.math.BigDecimal;
import java.util.List;


public interface IProductService {
    /**
     *
     * @return all Product objects
     */
    public abstract List<ProductDto> getProducts();

//    /**
//     *
//     * @param id
//     * @return get ProductDto object by using id
//     */
//    public abstract ProductDto getProduct(int productId);

    public abstract List<ProductDto> getProductBySize(int size);

    public abstract ProductPageResponse getProductsByCategory(String category, int page, int size);

    public abstract ProductPageResponse searchProducts(String category, int page, int size, String search, BigDecimal maxPrice, String sortBy, String sortDirection);

    public abstract ProductDto getProductById(long id);

    public abstract List<ProductDto> getRelatedProducts(String category, int size, long excludeId );
}
