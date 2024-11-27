package com.blackcode.sda_project.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardUIController {

    @FXML
    private Button attendanceButton;

    @FXML
    private Button classButton;

    @FXML
    private Button examButton;

    @FXML
    private Button feeButton;

    @FXML
    private Button leaveButton;

    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button registrationButton;

    @FXML
    private Button reportButton;



    @FXML
    void onClassClick(ActionEvent event) throws IOException {
        classButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-class.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onExamClick(ActionEvent event) throws IOException {
        examButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-exam.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void onLeaveClick(ActionEvent event) throws IOException {
        leaveButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-leave.fxml"));
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
    void onRegistrationClick(ActionEvent event) throws IOException {
        registrationButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-registration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
