package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.RegisterRequest;
import com.jntuh.exammanagement.dto.UserResponse;
import com.jntuh.exammanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.of(true, "Users fetched successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.of(true, "User fetched successfully", user));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse> getUsersByRole(@PathVariable String role) {
        List<UserResponse> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(ApiResponse.of(true, "Users by role fetched successfully", users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse user = userService.updateUser(id, registerRequest);
        return ResponseEntity.ok(ApiResponse.of(true, "User updated successfully", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.of(true, "User deleted successfully", null));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleUserStatus(@PathVariable Long id) {
        UserResponse user = userService.toggleUserStatus(id);
        return ResponseEntity.ok(ApiResponse.of(true, "User status toggled successfully", user));
    }
}
