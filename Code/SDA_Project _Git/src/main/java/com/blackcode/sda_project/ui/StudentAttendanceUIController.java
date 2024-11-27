package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.AttendanceService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Attendance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class StudentAttendanceUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Attendance, String> courseCol;

    @FXML
    private TableColumn<Attendance, LocalDate> dateCol;

    @FXML
    private TableColumn<Attendance, String> statusCol;

    @FXML
    private TableView<Attendance> attendanceTable;

    private final AttendanceService attendanceService = new AttendanceService();

    @FXML
    public void initialize() {
        // Initialize table columns with attendance properties
        courseCol.setCellValueFactory(data -> data.getValue().courseNameProperty());
        dateCol.setCellValueFactory(data -> data.getValue().dateProperty());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

        // Load attendance for the logged-in student
        loadStudentAttendance();
    }

    private void loadStudentAttendance() {
        try {
            int studentId = SessionManager.getInstance().getUserId();
            System.out.println("Student ID: " + studentId); // Debug log
            System.out.println("User Type: " + SessionManager.getInstance().getUserType()); // Debug log

            if (SessionManager.getInstance().getUserType() != SessionManager.UserType.STUDENT) {
                showError("Invalid access. Only students can view attendance.");
                return;
            }

            List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(studentId);
            ObservableList<Attendance> observableList = FXCollections.observableArrayList(attendanceList);
            attendanceTable.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace for debugging
            showError("Error loading attendance: " + e.getMessage());
        }
    }


    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
