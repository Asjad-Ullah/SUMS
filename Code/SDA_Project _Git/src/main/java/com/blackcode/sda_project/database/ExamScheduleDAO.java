package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.ExamSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExamScheduleDAO {

    // Fetch all exam schedules
    public static List<ExamSchedule> getAllExamSchedules() throws Exception {
        List<ExamSchedule> schedules = new ArrayList<>();
        String query = "SELECT es.exam_date, es.exam_day, es.start_time, es.end_time, es.room_no, c.course_name " +
                "FROM exam_schedule es " +
                "JOIN courses c ON es.course_id = c.course_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ExamSchedule schedule = new ExamSchedule();
                schedule.setCourse(rs.getString("course_name"));
                schedule.setDate(rs.getDate("exam_date").toString());
                schedule.setDay(rs.getString("exam_day"));
                schedule.setStartTime(rs.getString("start_time"));
                schedule.setEndTime(rs.getString("end_time"));
                schedule.setRoomNo(rs.getString("room_no"));

                // Additional setters for LocalDate and LocalTime
                schedule.setExamDate(rs.getDate("exam_date").toLocalDate());
                schedule.setExamStartTime(rs.getTime("start_time").toLocalTime());
                schedule.setExamEndTime(rs.getTime("end_time").toLocalTime());

                schedules.add(schedule);
            }
        }
        return schedules;
    }

    // Check if a course already has a schedule
    public static boolean isCourseScheduled(String courseName) throws Exception {
        String query = "SELECT COUNT(*) FROM exam_schedule es JOIN courses c ON es.course_id = c.course_id WHERE c.course_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Check if there is a room conflict
    public static boolean isRoomConflict(LocalDate date, String startTime, String endTime, String roomNo, String courseName) throws Exception {
        String query = "SELECT COUNT(*) FROM exam_schedule es " +
                "JOIN courses c ON es.course_id = c.course_id " +
                "WHERE es.exam_date = ? AND es.room_no = ? AND c.course_name != ? AND " +
                "(es.start_time < ? AND es.end_time > ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, date.toString());
            pstmt.setString(2, roomNo);
            pstmt.setString(3, courseName); // Exclude the current course
            pstmt.setString(4, endTime);
            pstmt.setString(5, startTime);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Add a new exam schedule
    public static void addExamSchedule(String courseName, String date, String day, String startTime, String endTime, String roomNo) throws Exception {
        String query = "INSERT INTO exam_schedule (course_id, exam_date, exam_day, start_time, end_time, room_no) " +
                "VALUES ((SELECT course_id FROM courses WHERE course_name = ?), ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseName);
            pstmt.setString(2, date);
            pstmt.setString(3, day);
            pstmt.setString(4, startTime);
            pstmt.setString(5, endTime);
            pstmt.setString(6, roomNo);
            pstmt.executeUpdate();
        }
    }

    // Update an existing exam schedule
    public static void updateExamSchedule(String courseName, LocalDate date, String day, String startTime, String endTime, String roomNo) throws Exception {
        String query = "UPDATE exam_schedule SET exam_date = ?, exam_day = ?, start_time = ?, end_time = ?, room_no = ? " +
                "WHERE course_id = (SELECT course_id FROM courses WHERE course_name = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, date.toString());
            pstmt.setString(2, day);
            pstmt.setString(3, startTime);
            pstmt.setString(4, endTime);
            pstmt.setString(5, roomNo);
            pstmt.setString(6, courseName);
            pstmt.executeUpdate();
        }
    }

    // Remove all schedules
    public static void removeAllSchedules() throws Exception {
        String query = "DELETE FROM exam_schedule";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        }
    }
}
