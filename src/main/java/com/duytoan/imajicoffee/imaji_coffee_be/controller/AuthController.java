package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.SignUpRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.LoginRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SignupStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.jwt.JwtUtil;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private final JwtUtil  jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDto requestDto) {
        String token = authService.login(requestDto);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto requestDto) {
        SignupStatus status = authService.singup(requestDto);
        return switch (status) {
            case SIGNUP_SUCCESS -> ResponseEntity.ok().body("User Register Successfully");
            case EMAIL_ALREADY_EXISTS -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken");
            case USERNAME_ALREADY_EXISTS ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        };
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
        }

        String token = authHeader.substring(7);

        try {
            if (jwtUtil.isTokenExpired(token)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
            }
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("valid", true));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
        }
    }
}
