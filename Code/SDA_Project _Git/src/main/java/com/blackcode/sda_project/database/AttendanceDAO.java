package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    private final List<Attendance> attendanceList = new ArrayList<>();

    // Insert new attendance
    public void insertAttendance(int courseId, int studentId, LocalDate date, String status) throws SQLException {
        String query = "INSERT INTO attendance (course_id, student_id, attendance_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        }
    }

    // Update existing attendance
    public void updateAttendance(int courseId, int studentId, LocalDate date, String status) throws SQLException {
        String query = "UPDATE attendance SET status = ? WHERE course_id = ? AND student_id = ? AND attendance_date = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, studentId);
            pstmt.setDate(4, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        }
    }

    // Fetch attendance records for a specific course and date
    // Fetch attendance records for a specific course and date
    public List<Attendance> getAttendanceByCourseAndDate(int courseId, LocalDate date) throws SQLException {
        String query = "SELECT a.*, s.name AS student_name, c.course_name " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.student_id " +
                "JOIN courses c ON a.course_id = c.course_id " +
                "WHERE a.course_id = ? AND a.attendance_date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setCourseId(rs.getInt("course_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setDate(rs.getDate("attendance_date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setStudentName(rs.getString("student_name"));
                    attendance.setCourseName(rs.getString("course_name")); // Set course name
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }

    // Fetch attendance records for a specific student
    public List<Attendance> getAttendanceByStudent(int studentId) throws SQLException {
        String query = "SELECT a.*, c.course_name FROM attendance a " +
                "JOIN courses c ON a.course_id = c.course_id " +
                "WHERE a.student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setCourseId(rs.getInt("course_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setDate(rs.getDate("attendance_date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setCourseName(rs.getString("course_name"));
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }



    // Get a single attendance record
    public Attendance getAttendanceRecord(int courseId, int studentId, LocalDate date) throws SQLException {
        String query = "SELECT * FROM attendance WHERE course_id = ? AND student_id = ? AND attendance_date = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.setDate(3, java.sql.Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setCourseId(rs.getInt("course_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setDate(rs.getDate("attendance_date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    return attendance;
                }
            }
        }
        return null;
    }
}
