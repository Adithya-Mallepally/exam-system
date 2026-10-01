package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.RoomRequest;
import com.jntuh.exammanagement.model.Room;
import com.jntuh.exammanagement.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(ApiResponse.of(true, "Rooms fetched successfully", rooms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Room fetched successfully", room));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        Room room = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.of(true, "Room created successfully", room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        Room room = roomService.updateRoom(id, request);
        return ResponseEntity.ok(ApiResponse.of(true, "Room updated successfully", room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Room deleted successfully", null));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse> getAvailableRooms() {
        List<Room> rooms = roomService.getAvailableRooms();
        return ResponseEntity.ok(ApiResponse.of(true, "Available rooms fetched successfully", rooms));
    }
}
