package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.SpaceDto;

import java.util.List;

public interface ISpaceService {
    /**
     *
     * @return list of SpaceDto objects
     */
    public abstract List<SpaceDto> getSpaces();

}
