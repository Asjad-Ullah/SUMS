package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.MarksDAO;
import com.blackcode.sda_project.database.StudentCourseEnrollmentDAO;
import com.blackcode.sda_project.database.StudentDAO;
import com.blackcode.sda_project.model_entity.Marks;
import com.blackcode.sda_project.model_entity.Student;

import java.util.List;

public class MarksService {

    private final MarksDAO marksDAO = new MarksDAO();
    private final StudentCourseEnrollmentDAO enrollmentDAO = new StudentCourseEnrollmentDAO();

    public void addOrUpdateMarks(int courseId, int studentId, String type, int obtainedMarks, int totalMarks) throws Exception {
        if (obtainedMarks > totalMarks) {
            throw new IllegalArgumentException("Obtained marks cannot exceed total marks.");
        }

        Marks existingMark = marksDAO.getMarksByStudentAndType(courseId, studentId, type);

        if (existingMark != null) {
            marksDAO.updateMarks(courseId, studentId, type, obtainedMarks, totalMarks);
        } else {
            marksDAO.insertMarks(courseId, studentId, type, obtainedMarks, totalMarks);
        }
    }

    public List<Marks> getMarksByStudent(int studentId) throws Exception {
        return marksDAO.getMarksByStudent(studentId);
    }

    public List<Student> getStudentsByCourse(int courseId) throws Exception {
        return enrollmentDAO.getEnrolledStudentsByCourse(courseId);
    }

    public List<Marks> getMarksForCourse(int courseId) throws Exception {
        return marksDAO.getMarksByCourse(courseId);
    }
}
