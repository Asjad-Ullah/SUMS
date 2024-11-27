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

import java.io.IOException;
import java.util.List;

public class AdminLeaveUIController {

    @FXML
    private Button approveButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<FacultyLeave, Integer> daysCol;

    @FXML
    private Button disapproveButton;

    @FXML
    private TableColumn<FacultyLeave, Integer> idCol;

    @FXML
    private TableView<FacultyLeave> leaveTable;

    @FXML
    private TableColumn<FacultyLeave, String> nameCol;

    @FXML
    private ChoiceBox<FacultyLeave> pendingLeaveDropbox;

    @FXML
    private TableColumn<FacultyLeave, String> reasonCol;

    @FXML
    private TableColumn<FacultyLeave, String> startdateCol;

    private final LeaveService leaveService = new LeaveService();

    @FXML
    public void initialize() {
        System.out.println("Initializing AdminLeaveUIController...");

        initializeTableColumns();
        loadPendingLeaves();
    }

    private void initializeTableColumns() {
        System.out.println("Initializing table columns...");

        // Bind table columns to FacultyLeave properties
        idCol.setCellValueFactory(data -> {
            System.out.println("Binding leaveId property for row: " + data.getValue().getLeaveId());
            return data.getValue().leaveIdProperty().asObject();
        });
        nameCol.setCellValueFactory(data -> {
            System.out.println("Binding facultyName property for row: " + data.getValue().getFacultyName());
            return data.getValue().facultyNameProperty();
        });
        startdateCol.setCellValueFactory(data -> {
            System.out.println("Binding startDate property for row: " + data.getValue().getStartDate());
            return data.getValue().startDateProperty().asString();
        });
        daysCol.setCellValueFactory(data -> {
            System.out.println("Binding days property for row: " + data.getValue().getDays());
            return data.getValue().daysProperty().asObject();
        });
        reasonCol.setCellValueFactory(data -> {
            System.out.println("Binding reason property for row: " + data.getValue().getReason());
            return data.getValue().reasonProperty();
        });
    }

    private void loadPendingLeaves() {
        System.out.println("Loading pending leaves...");
        try {
            // Load pending leaves
            List<FacultyLeave> pendingLeaves = leaveService.getPendingLeaves();
            System.out.println("Fetched pending leaves: " + pendingLeaves);

            ObservableList<FacultyLeave> leaveList = FXCollections.observableArrayList(pendingLeaves);
            leaveTable.setItems(leaveList); // Populate table
            pendingLeaveDropbox.setItems(leaveList); // Populate dropdown

            System.out.println("Pending leaves successfully loaded into table and dropdown.");
        } catch (Exception e) {
            System.err.println("Error loading pending leaves: " + e.getMessage());
            showError("Error loading leaves: " + e.getMessage());
        }
    }

    @FXML
    void onApproveClick(ActionEvent event) {
        System.out.println("Approve button clicked.");
        try {
            FacultyLeave selectedLeave = pendingLeaveDropbox.getValue();
            System.out.println("Selected leave to approve: " + selectedLeave);

            if (selectedLeave == null) {
                System.out.println("No leave selected for approval.");
                showError("Please select a leave to approve.");
                return;
            }

            leaveService.updateLeaveStatus(selectedLeave.getLeaveId(), "approved");
            System.out.println("Leave approved successfully for ID: " + selectedLeave.getLeaveId());
            showSuccess("Leave approved successfully!");
            loadPendingLeaves(); // Refresh the table and dropdown
        } catch (Exception e) {
            System.err.println("Error approving leave: " + e.getMessage());
            showError("Error approving leave: " + e.getMessage());
        }
    }

    @FXML
    void onDisapproveClick(ActionEvent event) {
        System.out.println("Disapprove button clicked.");
        try {
            FacultyLeave selectedLeave = pendingLeaveDropbox.getValue();
            System.out.println("Selected leave to disapprove: " + selectedLeave);

            if (selectedLeave == null) {
                System.out.println("No leave selected for disapproval.");
                showError("Please select a leave to disapprove.");
                return;
            }

            leaveService.updateLeaveStatus(selectedLeave.getLeaveId(), "disapproved");
            System.out.println("Leave disapproved successfully for ID: " + selectedLeave.getLeaveId());
            showSuccess("Leave disapproved successfully!");
            loadPendingLeaves(); // Refresh the table and dropdown
        } catch (Exception e) {
            System.err.println("Error disapproving leave: " + e.getMessage());
            showError("Error disapproving leave: " + e.getMessage());
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        System.out.println("Back button clicked.");
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
        System.out.println("Navigated back to admin dashboard.");
    }

    private void showError(String message) {
        System.err.println("Error: " + message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        System.out.println("Success: " + message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
