package com.jntuh.exammanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportData {
    private String examName;
    private String courseCode;
    private String courseName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;
    private String building;
    private String invigilatorName;
    private Integer seatCount;
}
