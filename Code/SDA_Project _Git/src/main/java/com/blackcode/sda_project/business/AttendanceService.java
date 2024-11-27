package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.AttendanceDAO;
import com.blackcode.sda_project.database.StudentCourseEnrollmentDAO;
import com.blackcode.sda_project.model_entity.Attendance;
import com.blackcode.sda_project.model_entity.Student;

import java.time.LocalDate;
import java.util.List;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final StudentCourseEnrollmentDAO enrollmentDAO = new StudentCourseEnrollmentDAO();

    public List<Attendance> getAttendanceByCourseAndDate(int courseId, LocalDate date) throws Exception {
        return attendanceDAO.getAttendanceByCourseAndDate(courseId, date);
    }

    public void markAttendance(int courseId, int studentId, LocalDate date, String status) throws Exception {
        Attendance existingAttendance = attendanceDAO.getAttendanceRecord(courseId, studentId, date);
        if (existingAttendance != null) {
            attendanceDAO.updateAttendance(courseId, studentId, date, status);
        } else {
            attendanceDAO.insertAttendance(courseId, studentId, date, status);
        }
    }

    public List<Student> getStudentsByCourse(int courseId) throws Exception {
        return enrollmentDAO.getEnrolledStudentsByCourse(courseId);
    }

    public List<Attendance> getAttendanceByStudent(int studentId) throws Exception {
        return attendanceDAO.getAttendanceByStudent(studentId);
    }

}
