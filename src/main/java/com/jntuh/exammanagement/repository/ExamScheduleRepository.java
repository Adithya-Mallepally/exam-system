package com.jntuh.exammanagement.repository;

import com.jntuh.exammanagement.model.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findByExamId(Long examId);
    List<ExamSchedule> findByRoomId(Long roomId);
    List<ExamSchedule> findByInvigilatorId(Long invigilatorId);
    List<ExamSchedule> findByDate(LocalDate date);
    List<ExamSchedule> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
