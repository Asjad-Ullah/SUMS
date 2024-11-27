package com.blackcode.sda_project.model_entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student extends User {
    private final IntegerProperty studentId = new SimpleIntegerProperty();
    private final StringProperty fatherName = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty cnic = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final IntegerProperty semester = new SimpleIntegerProperty();

    // Getter and Setter for Student ID
    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentId(int studentId) {
        this.studentId.set(studentId);
    }

    public IntegerProperty studentIdProperty() {
        return studentId;
    }

    // Getter and Setter for Father Name
    public String getFatherName() {
        return fatherName.get();
    }

    public void setFatherName(String fatherName) {
        this.fatherName.set(fatherName);
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    // Getter and Setter for Phone Number
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    // Getter and Setter for CNIC
    public String getCnic() {
        return cnic.get();
    }

    public void setCnic(String cnic) {
        this.cnic.set(cnic);
    }

    public StringProperty cnicProperty() {
        return cnic;
    }

    // Getter and Setter for City
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
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
        return getName(); // Use the getter method
    }

}
