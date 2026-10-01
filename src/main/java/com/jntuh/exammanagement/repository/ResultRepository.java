package com.jntuh.exammanagement.repository;

import com.jntuh.exammanagement.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByExamId(Long examId);
    List<Result> findByStudentId(Long studentId);
    Optional<Result> findByExamIdAndStudentId(Long examId, Long studentId);
}
