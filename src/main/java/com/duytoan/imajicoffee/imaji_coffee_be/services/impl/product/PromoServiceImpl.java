package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.product;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.PromoDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ProductPromosDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Promo;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.product.PromoRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.product.IPromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements IPromoService {
    private final PromoRepository promoRepository;
    @Override
    public ProductPromosDto getPromosForProduct(Long productId) {
        List<PromoDto> availablePromos = promoRepository
                .findAvailablePromosByProductId(productId)
                .stream().map(this::mapToPromoDto).toList();
        List<PromoDto> unavailablePromos = promoRepository
                .findUnavailablePromosByProductId(productId)
                .stream().map(this::mapToPromoDto).toList();
        return new ProductPromosDto(productId, availablePromos, unavailablePromos);
    }

    private PromoDto mapToPromoDto(Promo promo) {
        PromoDto promoDto = new PromoDto();
        BeanUtils.copyProperties(promo, promoDto);
        return promoDto;
    }
}
