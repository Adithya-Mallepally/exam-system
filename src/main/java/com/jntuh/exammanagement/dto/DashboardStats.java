package com.jntuh.exammanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
    private long totalStudents;
    private long totalFaculty;
    private long totalExams;
    private long upcomingExams;
    private long completedExams;
    private long totalRooms;
    private long availableRooms;
    private long totalCourses;
}
