package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.StudentCourseEnrollment;
import com.blackcode.sda_project.model_entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseEnrollmentDAO {


    // Get all enrolled courses
    public List<StudentCourseEnrollment> getEnrolledCourses(int studentId) throws Exception {
        List<StudentCourseEnrollment> enrolledCourses = new ArrayList<>();
        String query = "SELECT sce.course_id, c.course_name, c.course_code, c.credit_hours, c.description, sce.status " +
                "FROM student_course_enrollment sce " +
                "JOIN courses c ON sce.course_id = c.course_id " +
                "WHERE sce.student_id = ? AND sce.status = 'active'"; // Added condition for active status

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StudentCourseEnrollment enrollment = new StudentCourseEnrollment();
                    enrollment.setCourseId(rs.getInt("course_id"));
                    enrollment.setCourseName(rs.getString("course_name"));
                    enrollment.setCourseCode(rs.getString("course_code"));
                    enrollment.setCreditHours(rs.getInt("credit_hours"));
                    enrollment.setDescription(rs.getString("description"));
                    enrollment.setStatus(rs.getString("status"));
                    enrolledCourses.add(enrollment);
                }
            }
        }
        return enrolledCourses;
    }

    // NEW METHOD: Get students enrolled in a course
    public List<Student> getEnrolledStudentsByCourse(int courseId) throws Exception {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.student_id, s.name, s.email, s.father_name, s.phone_number, s.cnic, s.city, s.semester " +
                "FROM student_course_enrollment sce " +
                "JOIN students s ON sce.student_id = s.student_id " +
                "WHERE sce.course_id = ? AND sce.status = 'active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setFatherName(rs.getString("father_name"));
                    student.setPhoneNumber(rs.getString("phone_number"));
                    student.setCnic(rs.getString("cnic"));
                    student.setCity(rs.getString("city"));
                    student.setSemester(rs.getInt("semester"));
                    students.add(student);
                }
            }
        }
        return students;
    }

    public List<Integer> getActiveEnrolledStudents(int courseId) throws Exception {
        List<Integer> studentIds = new ArrayList<>();
        String query = "SELECT student_id FROM student_course_enrollment " +
                "WHERE course_id = ? AND status = 'active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    studentIds.add(rs.getInt("student_id"));
                }
            }
        }
        return studentIds;
    }

    // Check if a course is already enrolled
    public boolean isCourseAlreadyEnrolled(int studentId, int courseId) throws Exception {
        String query = "SELECT 1 FROM student_course_enrollment " +
                "WHERE student_id = ? AND course_id = ? AND status = 'active'"; // Check for active status only

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Return true only if an active enrollment exists
            }
        }
    }

    // Check if a student passed a course
    public boolean hasPassedCourse(int studentId, int courseId) throws Exception {
        String query = "SELECT 1 FROM student_course_enrollment " +
                "WHERE student_id = ? AND course_id = ? AND status = 'pass'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Enroll student in a course
    public void enrollStudentInCourse(int studentId, int courseId) throws Exception {
        String query = "INSERT INTO student_course_enrollment (student_id, course_id, status) VALUES (?, ?, 'active')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            pstmt.executeUpdate();
        }
    }

    // Get the current semester of the student
    public int getStudentSemester(int studentId) throws Exception {
        String query = "SELECT semester FROM students WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("semester");
                } else {
                    throw new IllegalArgumentException("Student not found with ID: " + studentId);
                }
            }
        }
    }
}
