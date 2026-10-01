package com.jntuh.exammanagement.config;

import com.jntuh.exammanagement.model.*;
import com.jntuh.exammanagement.model.enums.ExamStatus;
import com.jntuh.exammanagement.model.enums.ExamType;
import com.jntuh.exammanagement.model.enums.Role;
import com.jntuh.exammanagement.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ExamScheduleRepository scheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminOpt = userRepository.findByUsername("admin");
        if (adminOpt.isEmpty()) {
            logger.info("Seeding demo data...");

            // Create Users
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Dr. K. Ramesh (Controller of Examinations)");
            admin.setDepartment("Administration");
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);

            User faculty1 = new User();
            faculty1.setUsername("faculty1");
            faculty1.setPassword(passwordEncoder.encode("faculty123"));
            faculty1.setFullName("Dr. Srinivas Reddy");
            faculty1.setDepartment("Computer Science");
            faculty1.setRole(Role.FACULTY);
            faculty1.setActive(true);
            faculty1.setCreatedAt(LocalDateTime.now());
            userRepository.save(faculty1);

            User faculty2 = new User();
            faculty2.setUsername("faculty2");
            faculty2.setPassword(passwordEncoder.encode("faculty123"));
            faculty2.setFullName("Dr. Lakshmi Devi");
            faculty2.setDepartment("Electronics");
            faculty2.setRole(Role.FACULTY);
            faculty2.setActive(true);
            faculty2.setCreatedAt(LocalDateTime.now());
            userRepository.save(faculty2);

            User student1 = new User();
            student1.setUsername("student1");
            student1.setPassword(passwordEncoder.encode("student123"));
            student1.setFullName("A. Kiran Kumar");
            student1.setDepartment("Computer Science");
            student1.setRole(Role.STUDENT);
            student1.setActive(true);
            student1.setCreatedAt(LocalDateTime.now());
            userRepository.save(student1);

            User student2 = new User();
            student2.setUsername("student2");
            student2.setPassword(passwordEncoder.encode("student123"));
            student2.setFullName("B. Priya");
            student2.setDepartment("Electronics");
            student2.setRole(Role.STUDENT);
            student2.setActive(true);
            student2.setCreatedAt(LocalDateTime.now());
            userRepository.save(student2);

            User student3 = new User();
            student3.setUsername("student3");
            student3.setPassword(passwordEncoder.encode("student123"));
            student3.setFullName("C. Rahul");
            student3.setDepartment("Mechanical");
            student3.setRole(Role.STUDENT);
            student3.setActive(true);
            student3.setCreatedAt(LocalDateTime.now());
            userRepository.save(student3);

            // Create Courses
            Course c1 = new Course();
            c1.setCode("CS301");
            c1.setName("Data Structures");
            c1.setDepartment("Computer Science");
            c1.setSemester(3);
            c1.setCreditHours(4);
            c1.setFaculty(faculty1);
            courseRepository.save(c1);

            Course c2 = new Course();
            c2.setCode("CS401");
            c2.setName("Machine Learning");
            c2.setDepartment("Computer Science");
            c2.setSemester(7);
            c2.setCreditHours(3);
            c2.setFaculty(faculty1);
            courseRepository.save(c2);

            Course c3 = new Course();
            c3.setCode("EC301");
            c3.setName("VLSI Design");
            c3.setDepartment("Electronics");
            c3.setSemester(5);
            c3.setCreditHours(4);
            c3.setFaculty(faculty2);
            courseRepository.save(c3);

            Course c4 = new Course();
            c4.setCode("CS302");
            c4.setName("Database Systems");
            c4.setDepartment("Computer Science");
            c4.setSemester(4);
            c4.setCreditHours(4);
            c4.setFaculty(faculty1);
            courseRepository.save(c4);

            // Create Rooms
            Room r1 = new Room();
            r1.setName("Room 101");
            r1.setBuilding("Main Block");
            r1.setFloor(1);
            r1.setCapacity(60);
            r1.setHasProjector(true);
            r1.setAvailable(true);
            roomRepository.save(r1);

            Room r2 = new Room();
            r2.setName("Room 201");
            r2.setBuilding("CSE Block");
            r2.setFloor(2);
            r2.setCapacity(40);
            r2.setHasProjector(true);
            r2.setAvailable(true);
            roomRepository.save(r2);

            Room r3 = new Room();
            r3.setName("Room 301");
            r3.setBuilding("ECE Block");
            r3.setFloor(3);
            r3.setCapacity(50);
            r3.setHasProjector(false);
            r3.setAvailable(true);
            roomRepository.save(r3);

            Room r4 = new Room();
            r4.setName("Auditorium");
            r4.setBuilding("Main Block");
            r4.setFloor(1);
            r4.setCapacity(200);
            r4.setHasProjector(true);
            r4.setAvailable(true);
            roomRepository.save(r4);

            // Create Exams
            Exam e1 = new Exam();
            e1.setCourse(c1);
            e1.setExamType(ExamType.MIDTERM);
            e1.setTotalMarks(25);
            e1.setPassingMarks(10);
            e1.setDurationMinutes(90);
            e1.setDate(LocalDate.now().plusDays(10));
            e1.setStartTime(LocalTime.of(10, 0));
            e1.setStatus(ExamStatus.SCHEDULED);
            e1.setInstructions("No calculators allowed.");
            e1.setCreatedBy(admin);
            e1.setCreatedAt(LocalDateTime.now());
            examRepository.save(e1);

            Exam e2 = new Exam();
            e2.setCourse(c2);
            e2.setExamType(ExamType.SEMESTER);
            e2.setTotalMarks(75);
            e2.setPassingMarks(30);
            e2.setDurationMinutes(180);
            e2.setDate(LocalDate.now().plusDays(15));
            e2.setStartTime(LocalTime.of(14, 0));
            e2.setStatus(ExamStatus.SCHEDULED);
            e2.setInstructions("Answer all questions.");
            e2.setCreatedBy(admin);
            e2.setCreatedAt(LocalDateTime.now());
            examRepository.save(e2);

            Exam e3 = new Exam();
            e3.setCourse(c3);
            e3.setExamType(ExamType.PRACTICAL);
            e3.setTotalMarks(50);
            e3.setPassingMarks(25);
            e3.setDurationMinutes(120);
            e3.setDate(LocalDate.now().plusDays(5));
            e3.setStartTime(LocalTime.of(9, 30));
            e3.setStatus(ExamStatus.SCHEDULED);
            e3.setInstructions("Bring your lab manuals.");
            e3.setCreatedBy(faculty2);
            e3.setCreatedAt(LocalDateTime.now());
            examRepository.save(e3);

            Exam e4 = new Exam();
            e4.setCourse(c4);
            e4.setExamType(ExamType.INTERNAL);
            e4.setTotalMarks(20);
            e4.setPassingMarks(8);
            e4.setDurationMinutes(60);
            e4.setDate(LocalDate.now().minusDays(2));
            e4.setStartTime(LocalTime.of(11, 0));
            e4.setStatus(ExamStatus.COMPLETED);
            e4.setInstructions("Online submission required.");
            e4.setCreatedBy(faculty1);
            e4.setCreatedAt(LocalDateTime.now().minusDays(10));
            examRepository.save(e4);

            // Create Schedules
            ExamSchedule s1 = new ExamSchedule();
            s1.setExam(e1);
            s1.setRoom(r1);
            s1.setInvigilator(faculty2);
            s1.setDate(e1.getDate());
            s1.setStartTime(e1.getStartTime());
            s1.setEndTime(e1.getStartTime().plusMinutes(e1.getDurationMinutes()));
            s1.setSeatCount(50);
            s1.setNotes("Strict invigilation required");
            scheduleRepository.save(s1);

            ExamSchedule s2 = new ExamSchedule();
            s2.setExam(e2);
            s2.setRoom(r4);
            s2.setInvigilator(faculty1);
            s2.setDate(e2.getDate());
            s2.setStartTime(e2.getStartTime());
            s2.setEndTime(e2.getStartTime().plusMinutes(e2.getDurationMinutes()));
            s2.setSeatCount(150);
            s2.setNotes("Provide extra writing pads if needed");
            scheduleRepository.save(s2);

            ExamSchedule s3 = new ExamSchedule();
            s3.setExam(e3);
            s3.setRoom(r3);
            s3.setInvigilator(faculty2);
            s3.setDate(e3.getDate());
            s3.setStartTime(e3.getStartTime());
            s3.setEndTime(e3.getStartTime().plusMinutes(e3.getDurationMinutes()));
            s3.setSeatCount(30);
            s3.setNotes("Lab setup confirmed");
            scheduleRepository.save(s3);

            ExamSchedule s4 = new ExamSchedule();
            s4.setExam(e4);
            s4.setRoom(r2);
            s4.setInvigilator(faculty1);
            s4.setDate(e4.getDate());
            s4.setStartTime(e4.getStartTime());
            s4.setEndTime(e4.getStartTime().plusMinutes(e4.getDurationMinutes()));
            s4.setSeatCount(40);
            s4.setNotes("Internal review");
            scheduleRepository.save(s4);

            logger.info("Demo data seeded successfully");
        }
    }
}
