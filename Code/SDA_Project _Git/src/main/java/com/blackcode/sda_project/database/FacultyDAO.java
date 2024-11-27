package com.blackcode.sda_project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyDAO {

    // Validate faculty credentials
    public boolean validateFaculty(String email, String password) {
        String query = "SELECT * FROM faculty WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a faculty member is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch faculty ID by email
    public int validateFacultyAndGetId(String email, String password) {
        String query = "SELECT faculty_id FROM faculty WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("faculty_id"); // Return faculty ID if valid credentials
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if validation fails
    }

    // Fetch faculty name by ID
    public String getFacultyNameById(int facultyId) {
        String query = "SELECT name FROM faculty WHERE faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, facultyId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name"); // Return faculty name if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no faculty member is found
    }
}
