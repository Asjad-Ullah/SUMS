package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    // Validate student credentials
    public boolean validateStudent(String email, String password) {
        String query = "SELECT * FROM students WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a student is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate student and return student ID
    public int validateStudentAndGetId(String email, String password) {
        String query = "SELECT student_id FROM students WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_id"); // Return student ID if credentials are valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no student is found
    }

    // Get student name by student ID
    public String getStudentNameById(int studentId) {
        String query = "SELECT name FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name"); // Return student name if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no student is found
    }

    // Save a new student to the database
    public void save(Student student) {
        String query = "INSERT INTO students (name, email, password, father_name, phone_number, cnic, city, semester) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getFatherName());
            pstmt.setString(5, student.getPhoneNumber());
            pstmt.setString(6, student.getCnic());
            pstmt.setString(7, student.getCity());
            pstmt.setInt(8, student.getSemester());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving student: " + e.getMessage());
        }
    }

    // Fetch student profile by ID
    public Student getStudentById(int studentId) throws Exception {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(rs.getString("password"));
                    student.setFatherName(rs.getString("father_name"));
                    student.setPhoneNumber(rs.getString("phone_number"));
                    student.setCnic(rs.getString("cnic"));
                    student.setCity(rs.getString("city"));
                    student.setSemester(rs.getInt("semester"));
                    return student;
                }
            }
        }
        throw new IllegalArgumentException("Student not found with ID: " + studentId);
    }

    // Update student password
    public void updatePassword(int studentId, String newPassword) throws Exception {
        String query = "UPDATE students SET password = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, studentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("No student found with ID: " + studentId);
            }
        }
    }
}
