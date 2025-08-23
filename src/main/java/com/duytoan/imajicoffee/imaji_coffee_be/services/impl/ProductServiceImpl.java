package com.duytoan.imajicoffee.imaji_coffee_be.services.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.ProductDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.ProductImageDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.Product;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.ProductImage;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.ProductRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    /**
     * @return all Product objects
     */
    @Override
    @Cacheable(value = "products")
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    private ProductDto mapToProductDto (Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        if (!product.getProductImages().isEmpty()) {
            Set<ProductImageDto> productImageDtoSet = product.getProductImages().stream().map(this::mapToProductImageDto).collect(Collectors.toSet());
            productDto.setImages(productImageDtoSet);
        }
        return productDto;
    }

    private ProductImageDto mapToProductImageDto (ProductImage productImage) {
        ProductImageDto productImageDto = new ProductImageDto();
        BeanUtils.copyProperties(productImage, productImageDto);
        return productImageDto;
    }
}
