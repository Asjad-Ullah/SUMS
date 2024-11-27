package com.blackcode.sda_project.model_entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Course {

    private final IntegerProperty courseId = new SimpleIntegerProperty();
    private final StringProperty courseName = new SimpleStringProperty();
    private final StringProperty courseCode = new SimpleStringProperty();
    private final IntegerProperty creditHours = new SimpleIntegerProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty semester = new SimpleIntegerProperty();

    // Constructor
    public Course() {
        // Default constructor
    }

    // Getter and Setter for Course ID
    public int getCourseId() {
        return courseId.get();
    }

    public void setCourseId(int courseId) {
        this.courseId.set(courseId);
    }

    public IntegerProperty courseIdProperty() {
        return courseId;
    }

    // Getter and Setter for Course Name
    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    // Getter and Setter for Course Code
    public String getCourseCode() {
        return courseCode.get();
    }

    public void setCourseCode(String courseCode) {
        this.courseCode.set(courseCode);
    }

    public StringProperty courseCodeProperty() {
        return courseCode;
    }

    // Getter and Setter for Credit Hours
    public int getCreditHours() {
        return creditHours.get();
    }

    public void setCreditHours(int creditHours) {
        this.creditHours.set(creditHours);
    }

    public IntegerProperty creditHoursProperty() {
        return creditHours;
    }

    // Getter and Setter for Description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Getter and Setter for Semester
    public int getSemester() {
        return semester.get();
    }

    public void setSemester(int semester) {
        this.semester.set(semester);
    }

    public IntegerProperty semesterProperty() {
        return semester;
    }

    @Override
    public String toString() {
        return courseName.get() + " (" + courseCode.get() + ")";
    }
}
