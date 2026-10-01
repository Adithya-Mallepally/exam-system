package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.ExamRequest;
import com.jntuh.exammanagement.model.Exam;
import com.jntuh.exammanagement.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(ApiResponse.of(true, "Exams fetched successfully", exams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Exam fetched successfully", exam));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createExam(@Valid @RequestBody ExamRequest request, Authentication authentication) {
        String username = authentication.getName();
        Exam exam = examService.createExam(request, username);
        return ResponseEntity.ok(ApiResponse.of(true, "Exam created successfully", exam));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateExam(@PathVariable Long id, @Valid @RequestBody ExamRequest request) {
        Exam exam = examService.updateExam(id, request);
        return ResponseEntity.ok(ApiResponse.of(true, "Exam updated successfully", exam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.ok(ApiResponse.of(true, "Exam deleted successfully", null));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getExamsByStatus(@PathVariable String status) {
        List<Exam> exams = examService.getExamsByStatus(status);
        return ResponseEntity.ok(ApiResponse.of(true, "Exams by status fetched successfully", exams));
    }
}
