package com.jntuh.exammanagement.controller;

import com.jntuh.exammanagement.dto.ApiResponse;
import com.jntuh.exammanagement.dto.DashboardStats;
import com.jntuh.exammanagement.model.Exam;
import com.jntuh.exammanagement.model.enums.ExamStatus;
import com.jntuh.exammanagement.model.enums.Role;
import com.jntuh.exammanagement.repository.CourseRepository;
import com.jntuh.exammanagement.repository.ExamRepository;
import com.jntuh.exammanagement.repository.RoomRepository;
import com.jntuh.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        long totalStudents = userRepository.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).count();
        long totalFaculty = userRepository.findAll().stream().filter(u -> u.getRole() == Role.FACULTY).count();
        long totalExams = examRepository.count();
        long upcomingExams = examRepository.findAll().stream().filter(e -> e.getStatus() == ExamStatus.SCHEDULED).count();
        long completedExams = examRepository.findAll().stream().filter(e -> e.getStatus() == ExamStatus.COMPLETED).count();
        long totalRooms = roomRepository.count();
        long availableRooms = roomRepository.findAll().stream().filter(r -> r.isAvailable()).count();
        long totalCourses = courseRepository.count();

        stats.setTotalStudents(totalStudents);
        stats.setTotalFaculty(totalFaculty);
        stats.setTotalExams(totalExams);
        stats.setUpcomingExams(upcomingExams);
        stats.setCompletedExams(completedExams);
        stats.setTotalRooms(totalRooms);
        stats.setAvailableRooms(availableRooms);
        stats.setTotalCourses(totalCourses);

        return ResponseEntity.ok(ApiResponse.of(true, "Dashboard stats fetched successfully", stats));
    }

    @GetMapping("/recent-activity")
    public ResponseEntity<ApiResponse> getRecentActivity() {
        List<Exam> recentExams = examRepository.findAll().stream()
                .sorted(Comparator.comparing(Exam::getCreatedAt).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.of(true, "Recent activity fetched successfully", recentExams));
    }
}
