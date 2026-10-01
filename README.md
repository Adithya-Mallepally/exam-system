# JNTUH Examination Management System

A fullstack automation platform built for Jawaharlal Nehru Technological University Hyderabad (JNTUH) that replaces manual exam coordination processes. The system spans the API layer, database, and admin frontend with end-to-end ownership across scheduling, room allocation, invigilation assignment, and one-click report generation.

---

## Architecture

| Layer | Technology |
|---|---|
| Backend | Java 26, Spring Boot 3.4, Spring Security 6 |
| Authentication | JWT (stateless, role-scoped) |
| Database | H2 embedded (dev), MySQL 8 (production) |
| ORM | Spring Data JPA, Hibernate |
| Frontend | Vanilla JavaScript, HTML5, CSS3 |
| Build | Apache Maven 3.9 |

---

## Role-Based Access Control

The platform implements a role-scoped access system where each user type interacts only with their designated portion of the system.

| Role | Access Level |
|---|---|
| ADMIN | Full platform control: user management, course/exam/room/schedule CRUD, dashboard analytics, report generation |
| FACULTY | Manage exams for assigned courses, view invigilation duties, upload question papers |
| STUDENT | View personal exam timetable, download hall tickets, view published results |

---

## Data Model

The system operates on six core entities:

- **User** -- Stores credentials, role, department, and activation status for admins, faculty, and students.
- **Course** -- Academic courses with code, name, semester, credit hours, and assigned faculty.
- **Exam** -- Examination instances linked to courses with type (midterm, semester, supplementary, practical, internal), marks, duration, date, and status lifecycle.
- **Room** -- Physical exam venues with building, floor, capacity, projector availability, and scheduling status.
- **ExamSchedule** -- Links exams to rooms with assigned invigilators, time slots, and seat counts.
- **Result** -- Student performance records with marks, grades, and publication timestamps.

---

## Quick Start

### Prerequisites

- Java 17 or higher (tested on Java 26)
- Apache Maven 3.8+
- No database installation required (uses embedded H2 by default)

### Run the Application

```
git clone https://github.com/Adithya-Mallepally/exam-system.git
cd exam-system
mvn spring-boot:run
```

The application starts at http://localhost:8080

### Default Login Credentials

| Role | Username | Password |
|---|---|---|
| Administrator | admin | admin123 |
| Faculty | faculty1 | faculty123 |
| Faculty | faculty2 | faculty123 |
| Student | student1 | student123 |
| Student | student2 | student123 |
| Student | student3 | student123 |

On first launch, the system automatically seeds demo data including users, courses, exams, rooms, and exam schedules.

---

## REST API Reference

All endpoints return responses in the format: `{ success: boolean, message: string, data: object }`.

### Authentication

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | /api/auth/login | Authenticate user and receive JWT token | No |
| POST | /api/auth/register | Register a new user account | No |

### User Management (ADMIN only)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/users | List all users |
| GET | /api/users/{id} | Get user by ID |
| GET | /api/users/role/{role} | Filter users by role |
| PUT | /api/users/{id} | Update user profile |
| DELETE | /api/users/{id} | Remove user |
| PATCH | /api/users/{id}/toggle-status | Toggle active/inactive status |

### Exam Management (ADMIN, FACULTY)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/exams | List all exams |
| GET | /api/exams/{id} | Get exam details |
| POST | /api/exams | Create new exam |
| PUT | /api/exams/{id} | Update exam |
| DELETE | /api/exams/{id} | Delete exam |
| GET | /api/exams/status/{status} | Filter exams by status |

### Schedule Management (ADMIN, FACULTY)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/schedules | List all schedules |
| POST | /api/schedules | Create schedule (assign room and invigilator) |
| PUT | /api/schedules/{id} | Update schedule |
| DELETE | /api/schedules/{id} | Remove schedule |
| GET | /api/schedules/date/{date} | Get schedules for a specific date |

### Room Management (ADMIN only)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/rooms | List all rooms |
| POST | /api/rooms | Add new room |
| PUT | /api/rooms/{id} | Update room details |
| DELETE | /api/rooms/{id} | Remove room |
| GET | /api/rooms/available | List available rooms |

### Course Management (ADMIN, FACULTY)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/courses | List all courses |
| POST | /api/courses | Create new course |
| PUT | /api/courses/{id} | Update course |
| DELETE | /api/courses/{id} | Delete course |

### Results Management (ADMIN, FACULTY)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/results | List all results |
| POST | /api/results | Publish student result |
| PUT | /api/results/{id} | Update result |
| DELETE | /api/results/{id} | Remove result |

### Dashboard and Reports (ADMIN only)

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/dashboard/stats | Real-time platform statistics |
| GET | /api/dashboard/recent-activity | Recent exam activity feed |
| GET | /api/reports/exam-schedule | Schedule report as JSON |
| GET | /api/reports/exam-schedule/csv | Download schedule report as CSV |

---

## Features

### Live Admin Dashboard
Real-time statistics panel displaying total students, faculty, exams (upcoming and completed), available rooms, and courses. Includes a recent activity feed showing the latest exam operations.

### One-Click Report Generation
The schedule management panel includes a one-click CSV download button that generates a comprehensive exam schedule report with course details, room assignments, invigilator names, and time slots.

### Automated Exam Coordination
Replace manual coordination by digitizing the entire flow: create exams, assign rooms based on capacity, allocate invigilators, and publish schedules -- all from a single dashboard.

### Security
- JWT-based stateless authentication with configurable token expiration
- BCrypt password hashing
- Role-based endpoint authorization enforced at the Spring Security filter chain level
- CORS enabled for cross-origin frontend integrations

---

## Production Deployment (MySQL)

To switch from H2 to MySQL:

1. Create a MySQL database:
```sql
CREATE DATABASE exam_management;
```

2. Set environment variables:
```
MYSQL_URL=jdbc:mysql://localhost:3306/exam_management?useSSL=false&serverTimezone=UTC
MYSQL_USER=your_user
MYSQL_PASS=your_password
```

3. Run with the production profile:
```
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## Project Structure

```
exam-system/
  pom.xml
  src/main/java/com/jntuh/exammanagement/
    ExamManagementApplication.java
    config/
      DataSeeder.java
      WebConfig.java
    controller/
      AuthController.java
      CourseController.java
      DashboardController.java
      ExamController.java
      ReportController.java
      RoomController.java
      ScheduleController.java
      UserController.java
    dto/
      ApiResponse.java
      CourseRequest.java
      DashboardStats.java
      ExamRequest.java
      JwtResponse.java
      LoginRequest.java
      RegisterRequest.java
      ReportData.java
      ResultRequest.java
      RoomRequest.java
      ScheduleRequest.java
      UserResponse.java
    exception/
      BadRequestException.java
      GlobalExceptionHandler.java
      ResourceNotFoundException.java
    model/
      Course.java
      Exam.java
      ExamSchedule.java
      Result.java
      Room.java
      User.java
      enums/
        ExamStatus.java
        ExamType.java
        Role.java
    repository/
      CourseRepository.java
      ExamRepository.java
      ExamScheduleRepository.java
      ResultRepository.java
      RoomRepository.java
      UserRepository.java
    security/
      CustomUserDetailsService.java
      JwtAuthenticationFilter.java
      JwtTokenProvider.java
      SecurityConfig.java
    service/
      AuthService.java
      CourseService.java
      ExamService.java
      ReportService.java
      RoomService.java
      ScheduleService.java
      UserService.java
  src/main/resources/
    application.yml
    static/
      index.html
      css/style.css
      js/app.js
      js/auth.js
      js/courses.js
      js/dashboard.js
      js/exams.js
      js/results.js
      js/rooms.js
      js/schedules.js
      js/users.js
      images/jntuh-campus.jpg
```

---

## License

This project was developed as part of the JNTUH examination automation initiative.
