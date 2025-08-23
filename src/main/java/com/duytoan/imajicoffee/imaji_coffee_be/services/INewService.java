package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.NewDto;

import java.util.List;

public interface INewService {
    public abstract List<NewDto> getPageNews(int page, int size);
}
