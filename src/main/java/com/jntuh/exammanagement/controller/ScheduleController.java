package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.ScheduleRequest;
import com.jntuh.exammanagement.model.ExamSchedule;
import com.jntuh.exammanagement.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllSchedules() {
        List<ExamSchedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(ApiResponse.of(true, "Schedules fetched successfully", schedules));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getScheduleById(@PathVariable Long id) {
        ExamSchedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Schedule fetched successfully", schedule));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createSchedule(@Valid @RequestBody ScheduleRequest request) {
        ExamSchedule schedule = scheduleService.createSchedule(request);
        return ResponseEntity.ok(ApiResponse.of(true, "Schedule created successfully", schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequest request) {
        ExamSchedule schedule = scheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(ApiResponse.of(true, "Schedule updated successfully", schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Schedule deleted successfully", null));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse> getSchedulesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ExamSchedule> schedules = scheduleService.getSchedulesByDate(date);
        return ResponseEntity.ok(ApiResponse.of(true, "Schedules for date fetched successfully", schedules));
    }
}
