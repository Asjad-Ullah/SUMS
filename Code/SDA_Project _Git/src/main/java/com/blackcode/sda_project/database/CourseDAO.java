package com.blackcode.sda_project.database;

import com.blackcode.sda_project.model_entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Fetch all course names
    public static List<String> getAllCourseNames() throws Exception {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_name FROM courses";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                courses.add(rs.getString("course_name"));
            }
        }
        return courses;
    }

    // Fetch course ID by course name
    public static int getCourseIdByName(String courseName) throws Exception {
        String query = "SELECT course_id FROM courses WHERE course_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, courseName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("course_id"); // Return course ID
                } else {
                    throw new IllegalArgumentException("Course not found: " + courseName);
                }
            }
        }
    }



    // Fetch courses by semester and previous semester
    public List<Course> getCoursesBySemester(int currentSemester, int previousSemester) throws Exception {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE semester IN (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, currentSemester);
            pstmt.setInt(2, previousSemester);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setCreditHours(rs.getInt("credit_hours"));
                    course.setDescription(rs.getString("description"));
                    course.setSemester(rs.getInt("semester"));
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    // Fetch the prerequisite course ID for a given course
    public int getPrerequisiteCourse(int courseId) throws Exception {
        String query = "SELECT prerequisite_course_id FROM course_prerequisites WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("prerequisite_course_id"); // Return prerequisite course ID
                }
                return 0; // No prerequisite
            }
        }
    }

    // Fetch a course by ID
    public Course getCourseById(int courseId) throws Exception {
        String query = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setCreditHours(rs.getInt("credit_hours"));
                    course.setDescription(rs.getString("description"));
                    course.setSemester(rs.getInt("semester"));
                    return course;
                } else {
                    throw new IllegalArgumentException("Course not found for ID: " + courseId);
                }
            }
        }
    }

    // Fetch all courses
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCreditHours(rs.getInt("credit_hours"));
                course.setDescription(rs.getString("description"));
                course.setSemester(rs.getInt("semester"));
                courses.add(course);
            }
        }
        return courses;
    }

    public List<Course> getCoursesByFacultyId(int facultyId) throws Exception {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.* " +
                "FROM courses c " +
                "JOIN faculty_courses fc ON c.course_id = fc.course_id " +
                "WHERE fc.faculty_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, facultyId); // Set the faculty_id parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setCreditHours(rs.getInt("credit_hours"));
                    course.setDescription(rs.getString("description"));
                    course.setSemester(rs.getInt("semester"));
                    courses.add(course);
                }
            }
        }
        return courses;
    }

}
