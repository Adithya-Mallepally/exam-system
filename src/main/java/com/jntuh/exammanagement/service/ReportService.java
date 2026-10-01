package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.ReportData;
import com.jntuh.exammanagement.model.ExamSchedule;
import com.jntuh.exammanagement.repository.ExamScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ExamScheduleRepository scheduleRepository;

    public List<ReportData> generateScheduleReport() {
        List<ExamSchedule> schedules = scheduleRepository.findAll();
        
        return schedules.stream().map(schedule -> {
            ReportData data = new ReportData();
            data.setExamName(schedule.getExam().getExamType().name());
            data.setCourseCode(schedule.getExam().getCourse().getCode());
            data.setCourseName(schedule.getExam().getCourse().getName());
            data.setDate(schedule.getDate());
            data.setStartTime(schedule.getStartTime());
            data.setEndTime(schedule.getEndTime());
            data.setRoomName(schedule.getRoom().getName());
            data.setBuilding(schedule.getRoom().getBuilding());
            data.setInvigilatorName(schedule.getInvigilator().getFullName());
            data.setSeatCount(schedule.getSeatCount());
            return data;
        }).collect(Collectors.toList());
    }

    public String generateScheduleReportCsv() {
        List<ReportData> data = generateScheduleReport();
        
        StringBuilder csv = new StringBuilder();
        csv.append("Exam Name,Course Code,Course Name,Date,Start Time,End Time,Room Name,Building,Invigilator Name,Seat Count\n");
        
        for (ReportData row : data) {
            csv.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%d\n",
                    row.getExamName(),
                    row.getCourseCode(),
                    row.getCourseName(),
                    row.getDate(),
                    row.getStartTime(),
                    row.getEndTime(),
                    row.getRoomName(),
                    row.getBuilding(),
                    row.getInvigilatorName(),
                    row.getSeatCount()
            ));
        }
        
        return csv.toString();
    }
}
