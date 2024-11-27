package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.LeaveService;
import com.blackcode.sda_project.model_entity.FacultyLeave;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FacultyLeaveUIController {

    @FXML
    private Button backButton;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<FacultyLeave, Integer> daysCol;

    @FXML
    private TextField daysText;

    @FXML
    private TableView<FacultyLeave> leaveTable;

    @FXML
    private TableColumn<FacultyLeave, String> reasonCol;

    @FXML
    private TextField reasonText;

    @FXML
    private Button requestButton;

    @FXML
    private TableColumn<FacultyLeave, LocalDate> startdateCol;

    @FXML
    private TableColumn<FacultyLeave, String> statusCol;

    private final LeaveService leaveService = new LeaveService();

    @FXML
    public void initialize() {
        // Initialize table columns
        startdateCol.setCellValueFactory(data -> data.getValue().startDateProperty());
        daysCol.setCellValueFactory(data -> data.getValue().daysProperty().asObject());
        reasonCol.setCellValueFactory(data -> data.getValue().reasonProperty());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

        loadLeaveData(); // Load leave requests
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/faculty-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    @FXML
    void onRequestClick(ActionEvent event) {
        try {
            // Validate and submit leave request
            String reason = reasonText.getText();
            LocalDate startDate = datepicker.getValue();
            int days = Integer.parseInt(daysText.getText());

            if (reason.isEmpty() || startDate == null) {
                showError("Please fill in all fields.");
                return;
            }

            leaveService.submitLeaveRequest(reason, startDate, days);

            showSuccess("Leave request submitted successfully!");
            loadLeaveData(); // Refresh the leave table

        } catch (NumberFormatException e) {
            showError("Please enter a valid number of days.");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void loadLeaveData() {
        try {
            List<FacultyLeave> leaves = leaveService.getLeaveRequests();
            ObservableList<FacultyLeave> leaveList = FXCollections.observableArrayList(leaves);
            leaveTable.setItems(leaveList);
        } catch (Exception e) {
            showError("Error loading leave data: " + e.getMessage());
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
