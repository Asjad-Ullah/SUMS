package com.blackcode.sda_project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // Validate admin credentials
    public boolean validateAdmin(String email, String password) {
        String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if an admin is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch admin ID by email
    public int getAdminIdByEmail(String email) {
        String query = "SELECT admin_id FROM admin WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("admin_id"); // Return admin ID if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no admin is found
    }

    // Fetch admin name by email
    public String getAdminNameByEmail(String email) {
        String query = "SELECT name FROM admin WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name"); // Return admin name if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no admin is found
    }
}
