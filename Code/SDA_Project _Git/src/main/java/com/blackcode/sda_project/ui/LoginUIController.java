package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.LoginService;
import com.blackcode.sda_project.core.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginUIController {

    @FXML
    private TextField emailText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordText;

    private final LoginService loginService = new LoginService(); // Business layer instance

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        try {
            String email = emailText.getText();
            String password = passwordText.getText();

            // Call the business layer to validate login
            String userRole = loginService.authenticateUser(email, password);

            if (userRole.equals("admin")) {
                loadDashboard("/com/blackcode/sda_project/admin-dashboard.fxml");
            } else if (userRole.equals("student")) {
                loadDashboard("/com/blackcode/sda_project/student-dashboard.fxml");
            } else if (userRole.equals("faculty")) {
                loadDashboard("/com/blackcode/sda_project/faculty-dashboard.fxml");
            } else {
                showError("Invalid login credentials. Please try again.");
            }
        } catch (Exception e) {
            showError("An error occurred during login: " + e.getMessage());
        }
    }

    private void loadDashboard(String dashboardPath) throws IOException {
        // Hide the login screen and show the dashboard
        loginButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(dashboardPath));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();

        // Debugging: Show session details (can be removed later)
        SessionManager session = SessionManager.getInstance();
        System.out.println("Logged in as: " + session.getName() + " (" + session.getUserType() + ")");
    }

    private void showError(String message) {
        // Show error message in an alert dialog
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
