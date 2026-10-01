package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.RegisterRequest;
import com.jntuh.exammanagement.dto.UserResponse;
import com.jntuh.exammanagement.exception.BadRequestException;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.User;
import com.jntuh.exammanagement.model.enums.Role;
import com.jntuh.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserResponse.from(user);
    }

    public List<UserResponse> getUsersByRole(String roleName) {
        try {
            Role role = Role.valueOf(roleName.toUpperCase());
            return userRepository.findByRole(role).stream()
                    .map(UserResponse::from)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role specified.");
        }
    }

    public UserResponse updateUser(Long id, RegisterRequest registerRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (!user.getUsername().equals(registerRequest.getUsername()) && userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (!user.getEmail().equals(registerRequest.getEmail()) && userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email Address already in use!");
        }

        user.setUsername(registerRequest.getUsername());
        if (registerRequest.getPassword() != null && !registerRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        }
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        try {
            user.setRole(Role.valueOf(registerRequest.getRole()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role specified.");
        }
        user.setDepartment(registerRequest.getDepartment());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        return UserResponse.from(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    public UserResponse toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setActive(!user.isActive());
        return UserResponse.from(userRepository.save(user));
    }
}
