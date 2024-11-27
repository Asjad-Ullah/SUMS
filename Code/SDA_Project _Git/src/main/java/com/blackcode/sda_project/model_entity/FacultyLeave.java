package com.blackcode.sda_project.model_entity;

import javafx.beans.property.*;

import java.time.LocalDate;

public class FacultyLeave {

    private final IntegerProperty leaveId = new SimpleIntegerProperty();
    private final StringProperty facultyName = new SimpleStringProperty();
    private final StringProperty reason = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final IntegerProperty days = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();

    public int getLeaveId() {
        return leaveId.get();
    }

    public void setLeaveId(int leaveId) {
        this.leaveId.set(leaveId);
    }

    public IntegerProperty leaveIdProperty() {
        return leaveId;
    }

    public String getFacultyName() {
        return facultyName.get();
    }

    public void setFacultyName(String facultyName) {
        this.facultyName.set(facultyName);
    }

    public StringProperty facultyNameProperty() {
        return facultyName;
    }

    public String getReason() {
        return reason.get();
    }

    public void setReason(String reason) {
        this.reason.set(reason);
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public int getDays() {
        return days.get();
    }

    public void setDays(int days) {
        this.days.set(days);
    }

    public IntegerProperty daysProperty() {
        return days;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
    @Override
    public String toString() {
        return "Leave ID: " + getLeaveId() + ", Faculty Name: " + getFacultyName();
    }
}
