package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.CourseDAO;
import com.blackcode.sda_project.model_entity.Course;

import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO = new CourseDAO();

    public List<Course> getCoursesByFacultyId(int facultyId) throws Exception {
        return courseDAO.getCoursesByFacultyId(facultyId);
    }

    public List<Course> getAllCourses() throws Exception {
        return courseDAO.getAllCourses();
    }


    public Course getCourseById(int courseId) throws Exception {
        return courseDAO.getCourseById(courseId);
    }
}
