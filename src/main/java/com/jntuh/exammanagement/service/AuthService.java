package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.JwtResponse;
import com.jntuh.exammanagement.dto.LoginRequest;
import com.jntuh.exammanagement.dto.RegisterRequest;
import com.jntuh.exammanagement.dto.UserResponse;
import com.jntuh.exammanagement.exception.BadRequestException;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.User;
import com.jntuh.exammanagement.model.enums.Role;
import com.jntuh.exammanagement.repository.UserRepository;
import com.jntuh.exammanagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", loginRequest.getUsername()));

        return JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .username(user.getUsername())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .build();
    }

    public UserResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email Address already in use!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        try {
            user.setRole(Role.valueOf(registerRequest.getRole()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role specified.");
        }
        user.setDepartment(registerRequest.getDepartment());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User result = userRepository.save(user);

        return UserResponse.from(result);
    }
}
