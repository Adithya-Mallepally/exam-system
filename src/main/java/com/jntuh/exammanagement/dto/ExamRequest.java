package com.jntuh.exammanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ExamRequest {
    @NotNull
    private Long courseId;

    @NotBlank
    private String examType;

    @NotNull
    private Integer totalMarks;

    private Integer passingMarks;

    @NotNull
    private Integer durationMinutes;

    private LocalDate date;
    private LocalTime startTime;
    private String status;
    private String instructions;
}
