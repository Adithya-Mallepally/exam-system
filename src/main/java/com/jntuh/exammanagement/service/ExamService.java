package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.ExamRequest;
import com.jntuh.exammanagement.exception.BadRequestException;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.Course;
import com.jntuh.exammanagement.model.Exam;
import com.jntuh.exammanagement.model.User;
import com.jntuh.exammanagement.model.enums.ExamStatus;
import com.jntuh.exammanagement.model.enums.ExamType;
import com.jntuh.exammanagement.repository.CourseRepository;
import com.jntuh.exammanagement.repository.ExamRepository;
import com.jntuh.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));
    }

    public Exam createExam(ExamRequest request, String username) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", request.getCourseId()));
                
        User createdBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Exam exam = new Exam();
        exam.setCourse(course);
        try {
            exam.setExamType(ExamType.valueOf(request.getExamType()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid exam type.");
        }
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setDate(request.getDate());
        exam.setStartTime(request.getStartTime());
        try {
            exam.setStatus(ExamStatus.valueOf(request.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid exam status.");
        }
        exam.setInstructions(request.getInstructions());
        exam.setCreatedBy(createdBy);
        exam.setCreatedAt(LocalDateTime.now());

        return examRepository.save(exam);
    }

    public Exam updateExam(Long id, ExamRequest request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", request.getCourseId()));

        exam.setCourse(course);
        try {
            exam.setExamType(ExamType.valueOf(request.getExamType()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid exam type.");
        }
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setDate(request.getDate());
        exam.setStartTime(request.getStartTime());
        try {
            exam.setStatus(ExamStatus.valueOf(request.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid exam status.");
        }
        exam.setInstructions(request.getInstructions());

        return examRepository.save(exam);
    }

    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));
        examRepository.delete(exam);
    }

    public List<Exam> getExamsByStatus(String statusName) {
        try {
            ExamStatus status = ExamStatus.valueOf(statusName.toUpperCase());
            return examRepository.findByStatus(status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid exam status.");
        }
    }
}
