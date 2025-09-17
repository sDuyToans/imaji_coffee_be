package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.LoginRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.SignUpRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.Role;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.UserRole;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.UserRoleId;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.RoleName;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SignupStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.jwt.JwtUtil;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.RoleRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.UserRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.UserRoleRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.security.CustomUserDetails;
import com.duytoan.imajicoffee.imaji_coffee_be.services.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public SignupStatus singup(SignUpRequestDto signUpRequestDto) {
        User existingUser = userRepository.findByUsername(signUpRequestDto.getEmail()).orElse(null);

        // 1. If email exists -> check whether guest or full user
        if (existingUser != null){
            if (!existingUser.isGuest()) {
                // Already a registered user -> block sign up
                return SignupStatus.EMAIL_ALREADY_EXISTS;
            }
            // 2, Upgrade guest user to full account
            existingUser.setUsername(signUpRequestDto.getUsername());
            existingUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
            existingUser.setGuest(false);
            userRepository.save(existingUser);

            // Assign role if missing
            assignDefaultRoleIfMissingUser(existingUser);

            return SignupStatus.SIGNUP_SUCCESS;
        }

        // 3. Normal signup for brand-new user
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setUsername(signUpRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setGuest(false);
        user.setCreatedBy("System");
        userRepository.save(user);

        assignDefaultRoleIfMissingUser(user);
        return SignupStatus.SIGNUP_SUCCESS;
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        // Authenticate User
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getLoginInput(), loginRequestDto.getPassword())
        );
        // return JWT token
        return jwtUtil.generateToken((CustomUserDetails) authentication.getPrincipal());
    }

    private void assignDefaultRoleIfMissingUser(User user) {
        if (!userRoleRepository.existsByUser_UserId(user.getUserId())) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", RoleName.ROLE_USER.toString()));

            UserRole link = new UserRole();
            link.setRole(userRole);
            link.setUser(user);
            link.setId(new UserRoleId(user.getUserId(), userRole.getRoleId()));
            link.setCreatedBy("System");
            userRoleRepository.save(link);
        }
    }
}
