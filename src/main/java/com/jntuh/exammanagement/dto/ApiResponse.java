package com.jntuh.exammanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ApiResponse of(boolean success, String message, Object data) {
        return new ApiResponse(success, message, data);
    }
}
