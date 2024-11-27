package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.StudentService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentProfileUIController {

    @FXML
    private Button backButton;

    @FXML
    private Label cnicLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label fathernameLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button passwordupdateButton;

    @FXML
    private Label phonenumberLabel;

    @FXML
    private Label semesterLabel;

    @FXML
    private Label studentnameLabel;

    private final StudentService studentService = new StudentService(); // Business layer instance

    @FXML
    public void initialize() {
        loadStudentProfile(); // Load the student profile when the UI is initialized
    }

    private void loadStudentProfile() {
        try {
            // Get the student ID from the SessionManager
            int studentId = SessionManager.getInstance().getUserId();

            // Fetch the student profile using the StudentService
            Student student = studentService.getStudentProfile(studentId);

            // Populate the UI with the fetched student details
            studentnameLabel.setText(student.getName());
            emailLabel.setText(student.getEmail());
            fathernameLabel.setText(student.getFatherName());
            phonenumberLabel.setText(student.getPhoneNumber());
            cnicLabel.setText(student.getCnic());
            semesterLabel.setText("Semester: " + student.getSemester());

        } catch (Exception e) {
            // Display an error alert if the profile couldn't be loaded
            showError("Error loading profile: " + e.getMessage());
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        // Navigate back to the student dashboard
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    @FXML
    void onUpdatePasswordClick(ActionEvent event) {
        try {
            String newPassword = passwordText.getText();

            // Validate that the password is not empty
            if (newPassword == null || newPassword.isEmpty()) {
                showError("Password cannot be empty.");
                return;
            }

            // Get the student ID from the SessionManager
            int studentId = SessionManager.getInstance().getUserId();

            // Update the password using the StudentService
            studentService.updatePassword(studentId, newPassword);

            // Display a success message
            showSuccess("Password updated successfully!");

            // Clear the password field
            passwordText.clear();

        } catch (Exception e) {
            // Display an error alert if the password couldn't be updated
            showError("Error updating password: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
