package com.jntuh.exammanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    @NotBlank
    private String name;

    private String building;
    private Integer floor;

    @NotNull
    private Integer capacity;

    private boolean hasProjector;
    private boolean available;
}
