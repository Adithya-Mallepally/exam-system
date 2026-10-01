package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.JwtResponse;
import com.jntuh.exammanagement.dto.LoginRequest;
import com.jntuh.exammanagement.dto.RegisterRequest;
import com.jntuh.exammanagement.dto.UserResponse;
import com.jntuh.exammanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.of(true, "User authenticated successfully", jwtResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return ResponseEntity.ok(ApiResponse.of(true, "User registered successfully", userResponse));
    }
}
