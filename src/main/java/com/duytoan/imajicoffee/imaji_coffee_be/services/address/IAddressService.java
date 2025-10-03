package com.duytoan.imajicoffee.imaji_coffee_be.services.address;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.Address;

import java.util.List;

public interface IAddressService {
    Address saveAddressForOder(AddressDto addressDto, Long userId);
    List<AddressDto> getAddressesForUser(String email);
}
