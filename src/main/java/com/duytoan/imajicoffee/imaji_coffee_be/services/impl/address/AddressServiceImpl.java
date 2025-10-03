package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.address;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.Address;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.address.AddressRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.UserRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.address.IAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;


    @Override
    @Transactional
    public Address saveAddressForOder(AddressDto addressDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", addressDto.userId().toString()));

        var existingAddress = addressRepository.findIdenticalAddress(
                userId,
                addressDto.country(),
                addressDto.city(),
                addressDto.street(),
                addressDto.postalCode(),
                addressDto.apartment(),
                addressDto.phoneNumber()
        );

        if (existingAddress.isPresent()){
            Address address = existingAddress.get();
            address.setCreatedAt(Instant.now());
            address.setCreatedBy(user.getUsername());
            if (addressDto.isDefault()){
                address.setDefault(true);
                addressRepository.save(address);
            }
            return address;
        }


        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        address.setUser(user);
        address.setCreatedAt(Instant.now());
        address.setCreatedBy(user.getUsername());
        long count = addressRepository.countByUser_UserId(user.getUserId());
        if (count == 0 || address.isDefault()){
            addressRepository.clearDefaultForUser(user.getUserId());
            address.setDefault(true);
        }
        return addressRepository.save(address);
    }

    @Override
    public List<AddressDto> getAddressesForUser(String userId) {
        var currentUser = userRepository.findById(Long.parseLong(userId));
        if (currentUser.isEmpty()) throw new ResourceNotFoundException("User", "userId", userId);

        return addressRepository.findByUser_UserId(currentUser.get().getUserId())
                .stream().map(this::mapToAddressDto)
                .toList();
    }

    private AddressDto mapToAddressDto(Address address) {
        AddressDto addressDto = new AddressDto(
                address.getUser().getUserId(),
                address.getName(),
                address.getCountry(),
                address.getProvince(),
                address.getCity(),
                address.getStreet(),
                address.getPostalCode(),
                address.getApartment(),
                address.getPhoneNumber(),
                address.isDefault()
        );
        return addressDto;
    }
}
