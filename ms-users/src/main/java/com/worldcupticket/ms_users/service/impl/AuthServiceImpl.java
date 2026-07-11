package com.worldcupticket.ms_users.service.impl;

import com.worldcupticket.ms_users.service.AuthService;
import com.worldcupticket.ms_users.service.JWTService;

import lombok.AllArgsConstructor;

import com.worldcupticket.ms_users.dto.LoginRequestDTO;
import com.worldcupticket.ms_users.dto.RegisterRequestDTO;
import com.worldcupticket.ms_users.domain.User;
import com.worldcupticket.ms_users.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.worldcupticket.ms_users.enums.UserType;
import com.worldcupticket.ms_users.dto.AuthResponseDTO;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        User user = new User();
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setUserType(UserType.ROLE_CLIENT);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return new AuthResponseDTO(jwtService.generateToken(user));
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isPasswordValid(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponseDTO(jwtService.generateToken(user));
    }

    private boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
