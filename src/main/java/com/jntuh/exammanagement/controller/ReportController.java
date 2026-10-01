package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.ReportData;
import com.jntuh.exammanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/exam-schedule")
    public ResponseEntity<ApiResponse> getExamScheduleReport() {
        List<ReportData> reportData = reportService.generateScheduleReport();
        return ResponseEntity.ok(ApiResponse.of(true, "Exam schedule report fetched successfully", reportData));
    }

    @GetMapping("/exam-schedule/csv")
    public ResponseEntity<String> getExamScheduleReportCsv() {
        String csvData = reportService.generateScheduleReportCsv();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exam_schedule_report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }
}
