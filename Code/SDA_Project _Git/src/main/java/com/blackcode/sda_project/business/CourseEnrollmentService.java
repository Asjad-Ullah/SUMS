package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.CourseDAO;
import com.blackcode.sda_project.database.StudentCourseEnrollmentDAO;
import com.blackcode.sda_project.model_entity.Course;
import com.blackcode.sda_project.model_entity.StudentCourseEnrollment;

import java.util.ArrayList;
import java.util.List;

public class CourseEnrollmentService {

    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentCourseEnrollmentDAO enrollmentDAO = new StudentCourseEnrollmentDAO();

    public List<Course> getEligibleCourses(int studentId) throws Exception {
        int currentSemester = enrollmentDAO.getStudentSemester(studentId);
        List<StudentCourseEnrollment> enrolledCourses = enrollmentDAO.getEnrolledCourses(studentId);

        if (!canEnroll(enrolledCourses)) {
            throw new IllegalArgumentException("You cannot enroll for new courses at this time.");
        }

        List<Course> eligibleCourses = courseDAO.getCoursesBySemester(currentSemester, currentSemester - 1);

        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : eligibleCourses) {
            if (arePrerequisitesMet(studentId, course.getCourseId())) {
                filteredCourses.add(course);
            }
        }

        return filteredCourses;
    }

    public void enrollCourse(int studentId, int courseId) throws Exception {
        List<StudentCourseEnrollment> enrolledCourses = enrollmentDAO.getEnrolledCourses(studentId);

        if (!canEnroll(enrolledCourses)) {
            throw new IllegalArgumentException("You cannot enroll for new courses at this time.");
        }
        if (enrollmentDAO.isCourseAlreadyEnrolled(studentId, courseId)) {
            throw new IllegalArgumentException("You have already enrolled in this course.");
        }
        if (!arePrerequisitesMet(studentId, courseId)) {
            throw new IllegalArgumentException("Prerequisite not met for this course.");
        }

        enrollmentDAO.enrollStudentInCourse(studentId, courseId);
    }

    public List<StudentCourseEnrollment> getEnrolledCourses(int studentId) throws Exception {
        return enrollmentDAO.getEnrolledCourses(studentId);
    }

    private boolean canEnroll(List<StudentCourseEnrollment> enrolledCourses) {
        int activeCourseCount = 0;
        for (StudentCourseEnrollment course : enrolledCourses) {
            if (course.getStatus().equalsIgnoreCase("active")) {
                activeCourseCount++;
            }
        }

        return activeCourseCount < 5;
    }

    private boolean arePrerequisitesMet(int studentId, int courseId) throws Exception {
        int prerequisiteCourseId = courseDAO.getPrerequisiteCourse(courseId);

        if (prerequisiteCourseId == 0) {
            return true;
        }

        return enrollmentDAO.hasPassedCourse(studentId, prerequisiteCourseId);
    }
}
