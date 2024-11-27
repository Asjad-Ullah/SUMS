package com.blackcode.sda_project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentDashboardUIController {

    @FXML
    private Button attendanceButton;

    @FXML
    private Button classButton;

    @FXML
    private Button enrollButton;

    @FXML
    private Button examButton;

    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button marksButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button reportButton;

    @FXML
    void onAttendanceClick(ActionEvent event) throws IOException {
        attendanceButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-attendance.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onClassClick(ActionEvent event) throws IOException {
        classButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-class.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onEnrollClick(ActionEvent event) throws IOException {
        enrollButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-course.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onExamClick(ActionEvent event) throws IOException {
        examButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-exam.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-marks.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onNotificationClick(ActionEvent event) {

    }

    @FXML
    void onProfileClick(ActionEvent event) throws IOException {
        profileButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-profile.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onReportClick(ActionEvent event) {

    }

}
