package com.duytoan.imajicoffee.imaji_coffee_be.controller.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.AccountOrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.user.UserDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.address.AddressServiceImpl;
import com.duytoan.imajicoffee.imaji_coffee_be.services.order.IOrderService;
import com.duytoan.imajicoffee.imaji_coffee_be.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AddressServiceImpl addressService;
    private final IOrderService orderService;;
    private final IUserService userService;

    @GetMapping("/address")
    public ResponseEntity<List<AddressDto>> getAddresses(Authentication authentication) {
        String userId = authentication.getName();
        List<AddressDto> addressDtoList = addressService.getAddressesForUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressDtoList);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<AccountOrderDetailResponseDto>> getOrders(Authentication authentication) {
        String userId = authentication.getName();
        List<AccountOrderDetailResponseDto> accountOrderDetailResponseDtos = orderService.getAccountOrders(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountOrderDetailResponseDtos);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        String userId = authentication.getName();
        UserDto userDto = userService.getUser(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }
}
