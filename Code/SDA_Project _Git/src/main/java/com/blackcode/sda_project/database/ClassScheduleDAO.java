package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.ClassSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassScheduleDAO {

    List<ClassSchedule> schedulesClass = new ArrayList<>();

    // Retrieve all class schedules
    public static List<ClassSchedule> getAllClassSchedules() throws Exception {
        List<ClassSchedule> classSchedules = new ArrayList<>();
        String query = "SELECT cs.day_of_week, cs.start_time, cs.end_time, cs.room_no, c.course_name " +
                "FROM class_schedule cs " +
                "JOIN courses c ON cs.course_id = c.course_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ClassSchedule schedule = new ClassSchedule();
                schedule.setCourse(rs.getString("course_name")); // Set course name
                schedule.setDay(rs.getString("day_of_week"));    // Set day
                schedule.setStartTime(rs.getString("start_time")); // Set start time
                schedule.setEndTime(rs.getString("end_time"));   // Set end time
                schedule.setRoomNo(rs.getString("room_no"));     // Set room number
                classSchedules.add(schedule);
            }
        }
        return classSchedules;
    }

    // Add a new class schedule
    public static void addClassSchedule(int courseId, String day, String startTime, String endTime, String roomNo) throws Exception {
        String query = "INSERT INTO class_schedule (course_id, day_of_week, start_time, end_time, room_no) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setString(2, day);
            pstmt.setString(3, startTime);
            pstmt.setString(4, endTime);
            pstmt.setString(5, roomNo);

            pstmt.executeUpdate();
        }
    }

    public static void deleteAllClassSchedules() throws Exception {
        String query = "DELETE FROM class_schedule";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate(); // Execute the delete query
        }
    }

    // Update an existing class schedule
    public static void updateClassSchedule(int courseId, String day, String startTime, String endTime, String roomNo) throws Exception {
        String query = "UPDATE class_schedule " +
                "SET start_time = ?, end_time = ?, room_no = ? " +
                "WHERE course_id = ? AND day_of_week = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startTime);
            pstmt.setString(2, endTime);
            pstmt.setString(3, roomNo);
            pstmt.setInt(4, courseId);
            pstmt.setString(5, day);

            pstmt.executeUpdate();
        }
    }

    // Check for room conflicts
    public static boolean isRoomConflict(String day, String startTime, String endTime, String roomNo) throws Exception {
        String query = "SELECT COUNT(*) FROM class_schedule " +
                "WHERE day_of_week = ? AND room_no = ? " +
                "AND (start_time < ? AND end_time > ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, day);
            pstmt.setString(2, roomNo);
            pstmt.setString(3, endTime);
            pstmt.setString(4, startTime);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Check if the same course is scheduled on the same day
    public static boolean isSameCourseScheduledOnSameDay(int courseId, String day) throws Exception {
        String query = "SELECT COUNT(*) FROM class_schedule WHERE course_id = ? AND day_of_week = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setString(2, day);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
