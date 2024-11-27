package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.Marks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    // Fetch a specific marks record for a student by course and type
    public Marks getMarksByStudentAndType(int courseId, int studentId, String type) throws SQLException {
        String query = "SELECT * FROM marks WHERE course_id = ? AND student_id = ? AND type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, type);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Marks marks = new Marks();
                    marks.setCourseId(rs.getInt("course_id"));
                    marks.setStudentId(rs.getInt("student_id"));
                    marks.setType(rs.getString("type"));
                    marks.setObtainedMarks(rs.getInt("obtained_marks"));
                    marks.setTotalMarks(rs.getInt("total_marks"));
                    return marks;
                }
            }
        }
        return null;
    }

    // MarksDAO.java
    public List<Marks> getMarksByStudent(int studentId) throws SQLException {
        String query = "SELECT m.*, c.course_name " +
                "FROM marks m " +
                "JOIN courses c ON m.course_id = c.course_id " +
                "WHERE m.student_id = ?";
        List<Marks> marksList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Marks marks = new Marks();
                    marks.setCourseId(rs.getInt("course_id"));
                    marks.setStudentId(rs.getInt("student_id"));
                    marks.setCourseName(rs.getString("course_name"));
                    marks.setType(rs.getString("type"));
                    marks.setObtainedMarks(rs.getInt("obtained_marks"));
                    marks.setTotalMarks(rs.getInt("total_marks"));
                    marksList.add(marks);
                }
            }
        }
        return marksList;
    }



    // Insert marks for a student
    public void insertMarks(int courseId, int studentId, String type, int obtainedMarks, int totalMarks) throws SQLException {
        String query = "INSERT INTO marks (course_id, student_id, type, obtained_marks, total_marks) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, type);
            pstmt.setInt(4, obtainedMarks);
            pstmt.setInt(5, totalMarks);
            pstmt.executeUpdate();
        }
    }

    // Update marks for a student
    public void updateMarks(int courseId, int studentId, String type, int obtainedMarks, int totalMarks) throws SQLException {
        String query = "UPDATE marks SET obtained_marks = ?, total_marks = ? WHERE course_id = ? AND student_id = ? AND type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, obtainedMarks);
            pstmt.setInt(2, totalMarks);
            pstmt.setInt(3, courseId);
            pstmt.setInt(4, studentId);
            pstmt.setString(5, type);
            pstmt.executeUpdate();
        }
    }

    // Fetch all marks for a course
    public List<Marks> getMarksByCourse(int courseId) throws SQLException {
        String query = "SELECT m.*, s.name AS student_name FROM marks m " +
                "JOIN students s ON m.student_id = s.student_id " +
                "WHERE m.course_id = ?";
        List<Marks> marksList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Marks marks = new Marks();
                    marks.setStudentName(rs.getString("student_name")); // Set student name dynamically
                    marks.setType(rs.getString("type"));
                    marks.setObtainedMarks(rs.getInt("obtained_marks"));
                    marks.setTotalMarks(rs.getInt("total_marks"));
                    marksList.add(marks);
                }
            }
        }
        return marksList;
    }

}
