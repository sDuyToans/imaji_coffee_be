package com.duytoan.imajicoffee.imaji_coffee_be.controller.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.AccountOrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.user.UserDto;
import com.duytoan.imajicoffee.imaji_coffee_be.security.CustomUserDetails;
import com.duytoan.imajicoffee.imaji_coffee_be.services.address.IAddressService;
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
import java.util.Map;

/**
 * Account controller
 * @author duytoan
 * @since 10/2025
 */
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAddressService addressService;
    private final IOrderService orderService;
    private final IUserService userService;

    /**
     * Get current user addresses
     * @param authentication -> authentication object
     * @return address list
     */
    @GetMapping("/address")
    public ResponseEntity<List<AddressDto>> getAddresses(Authentication authentication) {
        String userId = authentication.getName();
        List<AddressDto> addressDtoList = addressService.getAddressesForUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressDtoList);
    }

    /**
     * Get current user orders
     * @param authentication -> auth object
     * @return order list
     */
    @GetMapping("/orders")
    public ResponseEntity<List<AccountOrderDetailResponseDto>> getOrders(Authentication authentication) {
        String userId = authentication.getName();
        List<AccountOrderDetailResponseDto> orderList = orderService.getAccountOrders(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderList);
    }

    /**
     * Get current user info
     * @param authentication -> auth object
     * @return user dto
     */
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        String userId = authentication.getName();
        UserDto userDto = userService.getUser(Long.parseLong(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    /**
     * Get user info from token saved in cookie
     * @param authentication -> auth object
     * @return Map of user information (username, email, roles)
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> myInfo(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getDetails();

        return ResponseEntity.ok(Map.of(
                "username", customUserDetails.getUsername(),
                "email", customUserDetails.getEmail(),
                "roles", customUserDetails.getAuthorities().toString()
        ));
    }
}
