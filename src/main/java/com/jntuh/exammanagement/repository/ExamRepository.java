package com.jntuh.exammanagement.repository;

import com.jntuh.exammanagement.model.Exam;
import com.jntuh.exammanagement.model.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseId(Long courseId);
    List<Exam> findByStatus(ExamStatus status);
    List<Exam> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Exam> findByCreatedById(Long userId);
    long countByStatus(ExamStatus status);
}
