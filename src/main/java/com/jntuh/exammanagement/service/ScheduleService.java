package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.ScheduleRequest;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.Exam;
import com.jntuh.exammanagement.model.ExamSchedule;
import com.jntuh.exammanagement.model.Room;
import com.jntuh.exammanagement.model.User;
import com.jntuh.exammanagement.repository.ExamRepository;
import com.jntuh.exammanagement.repository.ExamScheduleRepository;
import com.jntuh.exammanagement.repository.RoomRepository;
import com.jntuh.exammanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ExamScheduleRepository scheduleRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ExamSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public ExamSchedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamSchedule", "id", id));
    }

    public ExamSchedule createSchedule(ScheduleRequest request) {
        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", request.getExamId()));
                
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", request.getRoomId()));
                
        User invigilator = userRepository.findById(request.getInvigilatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getInvigilatorId()));

        ExamSchedule schedule = new ExamSchedule();
        schedule.setExam(exam);
        schedule.setRoom(room);
        schedule.setInvigilator(invigilator);
        schedule.setDate(request.getDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setSeatCount(request.getSeatCount());
        schedule.setNotes(request.getNotes());

        return scheduleRepository.save(schedule);
    }

    public ExamSchedule updateSchedule(Long id, ScheduleRequest request) {
        ExamSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamSchedule", "id", id));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", request.getExamId()));
                
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", request.getRoomId()));
                
        User invigilator = userRepository.findById(request.getInvigilatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getInvigilatorId()));

        schedule.setExam(exam);
        schedule.setRoom(room);
        schedule.setInvigilator(invigilator);
        schedule.setDate(request.getDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setSeatCount(request.getSeatCount());
        schedule.setNotes(request.getNotes());

        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        ExamSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamSchedule", "id", id));
        scheduleRepository.delete(schedule);
    }

    public List<ExamSchedule> getSchedulesByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }
}
