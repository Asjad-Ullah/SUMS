package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.StudentService;
import com.blackcode.sda_project.model_entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminRegistrationUIController {

    @FXML
    private Button backButton;

    @FXML
    private TextField cityText;

    @FXML
    private TextField cnicText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fatherText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField phoneText;

    @FXML
    private Button registrationBtn;

    private final StudentService studentService = new StudentService(); // Business layer instance

    @FXML
    void onRegistrationClick(ActionEvent event) {
        try {
            // Get input values
            String name = nameText.getText();
            String email = emailText.getText();
            String fatherName = fatherText.getText();
            String phoneNumber = phoneText.getText();
            String cnic = cnicText.getText();
            String city = cityText.getText();

            // Create a new Student object
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setFatherName(fatherName);
            student.setPhoneNumber(phoneNumber);
            student.setCnic(cnic);
            student.setCity(city);

            // Pass to business layer
            studentService.registerStudent(student);

            // Show success message
            showSuccess("Student registered successfully!");
            clearFields();

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            showError(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            showError("An error occurred during registration: " + e.getMessage());
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameText.clear();
        emailText.clear();
        fatherText.clear();
        phoneText.clear();
        cnicText.clear();
        cityText.clear();
    }
}
