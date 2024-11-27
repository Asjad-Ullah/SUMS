package com.blackcode.sda_project.model_entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassSchedule {

    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty day = new SimpleStringProperty();
    private final StringProperty startTime = new SimpleStringProperty();
    private final StringProperty endTime = new SimpleStringProperty();
    private final StringProperty roomNo = new SimpleStringProperty();

    // Getter and Setter for Course
    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public StringProperty courseProperty() {
        return course;
    }

    // Getter and Setter for Day
    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public StringProperty dayProperty() {
        return day;
    }

    // Getter and Setter for Start Time
    public String getStartTime() {
        return startTime.get();
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    // Getter and Setter for End Time
    public String getEndTime() {
        return endTime.get();
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    // Getter and Setter for Room No
    public String getRoomNo() {
        return roomNo.get();
    }

    public void setRoomNo(String roomNo) {
        this.roomNo.set(roomNo);
    }

    public StringProperty roomNoProperty() {
        return roomNo;
    }
}
