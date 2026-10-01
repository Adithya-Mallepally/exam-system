package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.CourseRequest;
import com.jntuh.exammanagement.exception.BadRequestException;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.Course;
import com.jntuh.exammanagement.model.User;
import com.jntuh.exammanagement.repository.CourseRepository;
import com.jntuh.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    public Course createCourse(CourseRequest request) {
        if (courseRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Course code already exists!");
        }

        Course course = new Course();
        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDepartment(request.getDepartment());
        course.setSemester(request.getSemester());
        course.setCreditHours(request.getCreditHours());

        if (request.getFacultyId() != null) {
            User faculty = userRepository.findById(request.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getFacultyId()));
            course.setFaculty(faculty);
        }

        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        if (!course.getCode().equals(request.getCode()) && courseRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Course code already exists!");
        }

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDepartment(request.getDepartment());
        course.setSemester(request.getSemester());
        course.setCreditHours(request.getCreditHours());

        if (request.getFacultyId() != null) {
            User faculty = userRepository.findById(request.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getFacultyId()));
            course.setFaculty(faculty);
        } else {
            course.setFaculty(null);
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        courseRepository.delete(course);
    }
}
