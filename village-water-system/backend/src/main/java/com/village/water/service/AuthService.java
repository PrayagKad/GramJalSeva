package com.village.water.service;

import com.village.water.dto.AuthDTO;
import com.village.water.entity.User;
import com.village.water.exception.ResourceNotFoundException;
import com.village.water.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest request) {
        User user = userRepository.findByPhoneAndPassword(request.getPhone(), request.getPassword())
            .orElseThrow(() -> new ResourceNotFoundException("Invalid phone or password"));

        return AuthDTO.LoginResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .build();
    }
}
