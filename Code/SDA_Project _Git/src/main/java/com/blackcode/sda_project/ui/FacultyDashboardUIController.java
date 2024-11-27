package com.blackcode.sda_project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FacultyDashboardUIController {

    @FXML
    private Button attendanceButton;

    @FXML
    private Button leaveButton;

    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button marksButton;

    @FXML
    void onAttendanceClick(ActionEvent event) throws IOException {
        attendanceButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/faculty-attendance.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onLeaveClick(ActionEvent event) throws IOException {
        leaveButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/faculty-leave.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onLogoutClick(ActionEvent event) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/login-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onMarksClick(ActionEvent event) throws IOException {
        marksButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/faculty-marks.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}




