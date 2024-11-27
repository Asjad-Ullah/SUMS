package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.StudentDAO;
import com.blackcode.sda_project.model_entity.Student;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    public void registerStudent(Student student) throws NoSuchAlgorithmException {
        validateStudent(student);

        String defaultPassword = "12345678";

        student.setPassword(this.hashPassword(defaultPassword));
        student.setSemester(1);

        studentDAO.save(student);
    }

    public Student getStudentProfile(int studentId) throws Exception {
        return studentDAO.getStudentById(studentId);
    }

    public void updatePassword(int studentId, String newPassword) throws Exception {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        studentDAO.updatePassword(studentId, hashPassword(newPassword));
    }

    private void validateStudent(Student student) {
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (!student.getEmail().endsWith("@student.edu.pk")) {
            throw new IllegalArgumentException("Email must end with '@student.edu.pk'.");
        }
        if (student.getFatherName() == null || student.getFatherName().isEmpty()) {
            throw new IllegalArgumentException("Father's Name is required.");
        }
        if (student.getPhoneNumber() == null || student.getPhoneNumber().length() != 11) {
            throw new IllegalArgumentException("Phone number must be exactly 11 digits.");
        }
        if (student.getCnic() == null || student.getCnic().length() != 13) {
            throw new IllegalArgumentException("CNIC must be exactly 13 digits.");
        }
        if (student.getCity() == null || student.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required.");
        }
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
