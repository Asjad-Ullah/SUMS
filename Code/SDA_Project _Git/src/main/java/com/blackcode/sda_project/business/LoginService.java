package com.blackcode.sda_project.business;

import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.database.AdminDAO;
import com.blackcode.sda_project.database.FacultyDAO;
import com.blackcode.sda_project.database.StudentDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginService {

    private final AdminDAO adminDAO = new AdminDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();

    public String authenticateUser(String email, String password) throws NoSuchAlgorithmException {
        SessionManager session = SessionManager.getInstance();

        if (email.endsWith("@admin.edu.pk")) {
            if (adminDAO.validateAdmin(email, hashPassword(password))) {
                session.setUserId(adminDAO.getAdminIdByEmail(email));
                session.setName(adminDAO.getAdminNameByEmail(email));
                session.setEmail(email);
                session.setUserType(SessionManager.UserType.ADMIN);
                return "admin";
            }
        } else if (email.endsWith("@student.edu.pk")) {
            int studentId = studentDAO.validateStudentAndGetId(email, hashPassword(password));
            if (studentId > 0) {
                session.setUserId(studentId);
                session.setName(studentDAO.getStudentNameById(studentId));
                session.setEmail(email);
                session.setUserType(SessionManager.UserType.STUDENT);
                return "student";
            }
        } else if (email.endsWith("@faculty.edu.pk")) {
            int facultyId = facultyDAO.validateFacultyAndGetId(email, hashPassword(password));
            if (facultyId > 0) {
                session.setUserId(facultyId);
                session.setName(facultyDAO.getFacultyNameById(facultyId));
                session.setEmail(email);
                session.setUserType(SessionManager.UserType.FACULTY);
                return "faculty";
            }
        }
        return "invalid";
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Get a MessageDigest instance for SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Hash the password
        byte[] hashedBytes = md.digest(password.getBytes());

        // Convert the hashed bytes to a Base64-encoded string
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
