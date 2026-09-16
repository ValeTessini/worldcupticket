package com.worldcupticket.ms_users.service.impl;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_users.dto.UserDTO;
import com.worldcupticket.ms_users.repository.UserRepository;
import com.worldcupticket.ms_users.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserById(Long id) {
        return UserDTO.from(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

}
