package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.FacultyLeave;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    // Existing method for inserting leave requests
    public void insertLeaveRequest(int facultyId, String reason, LocalDate startDate, int days) throws Exception {
        String query = "INSERT INTO faculty_leave (faculty_id, reason, start_date, days, status) VALUES (?, ?, ?, ?, 'pending')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, facultyId);
            pstmt.setString(2, reason);
            pstmt.setDate(3, java.sql.Date.valueOf(startDate));
            pstmt.setInt(4, days);

            pstmt.executeUpdate();
        }
    }

    // Existing method for retrieving leave requests for a specific faculty
    public List<FacultyLeave> getLeaveRequestsForFaculty(int facultyId) throws Exception {
        List<FacultyLeave> leaveRequests = new ArrayList<>();
        String query = "SELECT * FROM faculty_leave WHERE faculty_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, facultyId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FacultyLeave leave = new FacultyLeave();
                    leave.setLeaveId(rs.getInt("leave_id"));
                    leave.setStartDate(rs.getDate("start_date").toLocalDate());
                    leave.setDays(rs.getInt("days"));
                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));

                    leaveRequests.add(leave);
                }
            }
        }
        return leaveRequests;
    }

    // New method: Retrieve all leave requests with a specific status (admin use case)
    public List<FacultyLeave> getLeavesByStatus(String status) throws Exception {
        List<FacultyLeave> leaveRequests = new ArrayList<>();
        String query = "SELECT fl.leave_id, fl.start_date, fl.days, fl.reason, fl.status, f.name AS faculty_name " +
                "FROM faculty_leave fl " +
                "JOIN faculty f ON fl.faculty_id = f.faculty_id " +
                "WHERE fl.status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FacultyLeave leave = new FacultyLeave();
                    leave.setLeaveId(rs.getInt("leave_id"));
                    leave.setStartDate(rs.getDate("start_date").toLocalDate());
                    leave.setDays(rs.getInt("days"));
                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));
                    leave.setFacultyName(rs.getString("faculty_name"));

                    leaveRequests.add(leave);
                }
            }
        }
        return leaveRequests;
    }

    // New method: Update the status of a leave request
    public void updateLeaveStatus(int leaveId, String status) throws Exception {
        String query = "UPDATE faculty_leave SET status = ? WHERE leave_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, leaveId);

            pstmt.executeUpdate();
        }
    }
}
