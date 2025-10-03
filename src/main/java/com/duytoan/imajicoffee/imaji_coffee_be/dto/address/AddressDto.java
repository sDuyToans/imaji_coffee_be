package com.duytoan.imajicoffee.imaji_coffee_be.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Setter;


public record AddressDto(

        @Setter
        @NotNull(message = "userId is required")
        Long userId,

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Country is required")
        String country,

        String province,
        String city,
        String street,

        @Pattern(regexp = "^[A-Za-z0-9 ]{3,10}$", message = "Postal code must be valid")
        String postalCode,

        String apartment,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
        String phoneNumber,

        Boolean isDefault
) {}
