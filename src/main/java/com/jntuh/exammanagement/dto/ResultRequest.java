package com.jntuh.exammanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultRequest {
    @NotNull
    private Long examId;

    @NotNull
    private Long studentId;

    @NotNull
    private Double marksObtained;

    private String grade;
    private String remarks;
}
