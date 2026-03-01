package com.duytoan.imajicoffee.imaji_coffee_be.services.address;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.Address;

import java.util.List;

/**
 * Address interface contains method's name and parameters
 * @author duytoan
 * @since 10/2025
 */
public interface IAddressService {

    /**
     * Save address for order
     * @param addressDto
     * @param userId
     * @return Address object
     */
    Address saveAddressForOder(AddressDto addressDto, Long userId);

    /**
     * Get addresses of user
     * @param userId
     * @return List of address dto object
     */
    List<AddressDto> getAddressesForUser(String userId);
}
