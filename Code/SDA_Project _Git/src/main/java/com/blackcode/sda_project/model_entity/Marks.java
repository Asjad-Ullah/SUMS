package com.blackcode.sda_project.model_entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Marks {
    private final IntegerProperty courseId = new SimpleIntegerProperty();
    private final IntegerProperty studentId = new SimpleIntegerProperty();
    private final StringProperty studentName = new SimpleStringProperty();
    private final StringProperty courseName = new SimpleStringProperty(); // Added for course name
    private final StringProperty type = new SimpleStringProperty();
    private final IntegerProperty obtainedMarks = new SimpleIntegerProperty();
    private final IntegerProperty totalMarks = new SimpleIntegerProperty();

    // Course ID
    public int getCourseId() {
        return courseId.get();
    }

    public void setCourseId(int courseId) {
        this.courseId.set(courseId);
    }

    public IntegerProperty courseIdProperty() {
        return courseId;
    }

    // Student ID
    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentId(int studentId) {
        this.studentId.set(studentId);
    }

    public IntegerProperty studentIdProperty() {
        return studentId;
    }

    // Student Name
    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    // Course Name
    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    // Type
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    // Obtained Marks
    public int getObtainedMarks() {
        return obtainedMarks.get();
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks.set(obtainedMarks);
    }

    public IntegerProperty obtainedMarksProperty() {
        return obtainedMarks;
    }

    // Total Marks
    public int getTotalMarks() {
        return totalMarks.get();
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks.set(totalMarks);
    }

    public IntegerProperty totalMarksProperty() {
        return totalMarks;
    }
}
