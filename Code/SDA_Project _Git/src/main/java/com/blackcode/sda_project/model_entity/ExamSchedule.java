package com.blackcode.sda_project.model_entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamSchedule {
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty day = new SimpleStringProperty();
    private final StringProperty startTime = new SimpleStringProperty();
    private final StringProperty endTime = new SimpleStringProperty();
    private final StringProperty roomNo = new SimpleStringProperty();

    // New Properties
    private final ObjectProperty<LocalDate> examDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> examStartTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> examEndTime = new SimpleObjectProperty<>();

    // Existing methods (unchanged)
    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public StringProperty courseProperty() {
        return course;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public StringProperty dayProperty() {
        return day;
    }

    public String getStartTime() {
        return startTime.get();
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public String getEndTime() {
        return endTime.get();
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    public String getRoomNo() {
        return roomNo.get();
    }

    public void setRoomNo(String roomNo) {
        this.roomNo.set(roomNo);
    }

    public StringProperty roomNoProperty() {
        return roomNo;
    }

    // Additional methods for LocalDate and LocalTime
    public LocalDate getExamDate() {
        return examDate.get();
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate.set(examDate);
    }

    public ObjectProperty<LocalDate> examDateProperty() {
        return examDate;
    }

    public LocalTime getExamStartTime() {
        return examStartTime.get();
    }

    public void setExamStartTime(LocalTime examStartTime) {
        this.examStartTime.set(examStartTime);
    }

    public ObjectProperty<LocalTime> examStartTimeProperty() {
        return examStartTime;
    }

    public LocalTime getExamEndTime() {
        return examEndTime.get();
    }

    public void setExamEndTime(LocalTime examEndTime) {
        this.examEndTime.set(examEndTime);
    }

    public ObjectProperty<LocalTime> examEndTimeProperty() {
        return examEndTime;
    }
}
