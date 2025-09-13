package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductPageResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        List<ProductDto> productDtoList;

        if (category != null) {
            ProductPageResponse response = productService.getProductsByCategory(category, page, size != null ? size : 16);
            return ResponseEntity.ok(response);
        } else if (size != null) {
            return ResponseEntity.ok(productService.getProductBySize(size));
        } else {
            return ResponseEntity.ok(productService.getProducts());
        }
    }

    @GetMapping("/search")
    public  ResponseEntity<ProductPageResponse> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue =  "8") Integer size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        ProductPageResponse productPageResponse = productService.searchProducts(category, page, size, search, maxPrice, sortBy, sortDirection);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPageResponse);
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDto);
    }

    @GetMapping("/related")
    public ResponseEntity<List<ProductDto>> getRelatedProducts(@RequestParam String category, @RequestParam Long excludedId, @RequestParam Integer size) {
        List<ProductDto> productDtoList = productService.getRelatedProducts(category, size, excludedId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDtoList);
    }
}
