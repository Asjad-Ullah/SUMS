package com.blackcode.sda_project.core;


public class SessionManager {

    // Enum to differentiate user types
    public enum UserType {
        STUDENT,
        FACULTY,
        ADMIN
    }

    private static SessionManager instance;
    private int userId; // Can be studentId or facultyId
    private String name;
    private String email;
    private UserType userType;

    private SessionManager() {
    }

    // Singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void clearSession() {
        this.userId = 0;
        this.name = null;
        this.email = null;
        this.userType = null;
    }
}
