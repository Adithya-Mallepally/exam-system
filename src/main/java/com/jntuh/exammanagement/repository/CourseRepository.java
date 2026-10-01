package com.jntuh.exammanagement.repository;

import com.jntuh.exammanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByFacultyId(Long facultyId);
    List<Course> findByDepartment(String department);
    List<Course> findBySemester(Integer semester);
    Optional<Course> findByCode(String code);
    boolean existsByCode(String code);
}
