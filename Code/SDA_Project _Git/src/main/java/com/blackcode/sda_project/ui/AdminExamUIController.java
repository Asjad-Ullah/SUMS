package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.ExamScheduleService;
import com.blackcode.sda_project.database.CourseDAO;
import com.blackcode.sda_project.database.ExamScheduleDAO;
import com.blackcode.sda_project.model_entity.ExamSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AdminExamUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<ExamSchedule, String> courseCol;

    @FXML
    private ChoiceBox<String> courseDropdown;

    @FXML
    private TableColumn<ExamSchedule, String> dateCol;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<ExamSchedule, String> endCol;

    @FXML
    private TableView<ExamSchedule> examScheduleTable;

    @FXML
    private TableColumn<ExamSchedule, String> roomNoCol;

    @FXML
    private TableColumn<ExamSchedule, String> startCol;

    @FXML
    private TextField roomNo;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private Button removeButton;

    @FXML
    private TableColumn<ExamSchedule, String> dayCol;

    private final ExamScheduleService examScheduleService = new ExamScheduleService();

    @FXML
    public void initialize() {
        populateCourses(); // Populate courses dropdown
        populateExamScheduleTable(); // Load exam schedules into the table
    }

    @FXML
    void onAddClick(ActionEvent event) {
        try {
            String course = courseDropdown.getValue();
            LocalDate selectedDate = datePicker.getValue();
            String day = getDayFromDatePicker();
            String startTimeInput = startTime.getText();
            String endTimeInput = endTime.getText();
            String roomNumber = roomNo.getText();

            if (course == null || selectedDate == null || startTimeInput.isEmpty() || endTimeInput.isEmpty() || roomNumber.isEmpty()) {
                showError("Please fill all the fields.");
                return;
            }

            // Business layer validation and adding/updating the schedule
            examScheduleService.addOrUpdateExamSchedule(course, selectedDate, day, startTimeInput, endTimeInput, roomNumber);

            // Refresh the table
            populateExamScheduleTable();

            showSuccess("Exam schedule added/updated successfully!");

        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Error adding/updating exam schedule: " + e.getMessage());
        }
    }

    @FXML
    void onRemoveClick(ActionEvent event) {
        try {
            // Call the ExamScheduleService to remove all schedules
            examScheduleService.removeAllExamSchedules();

            // Refresh the table
            populateExamScheduleTable();

            // Show success message
            showSuccess("All exam schedules have been removed.");
        } catch (Exception e) {
            showError("Error removing exam schedules: " + e.getMessage());
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

    private void populateCourses() {
        try {
            List<String> courses = CourseDAO.getAllCourseNames();
            courseDropdown.getItems().addAll(courses);
        } catch (Exception e) {
            showError("Error fetching courses: " + e.getMessage());
        }
    }

    private void populateExamScheduleTable() {
        try {
            List<ExamSchedule> examSchedules = ExamScheduleDAO.getAllExamSchedules();
            ObservableList<ExamSchedule> observableSchedules = FXCollections.observableArrayList(examSchedules);

            // Bind table columns to properties
            courseCol.setCellValueFactory(data -> data.getValue().courseProperty());
            dateCol.setCellValueFactory(data -> data.getValue().dateProperty());
            dayCol.setCellValueFactory(data -> data.getValue().dayProperty());
            startCol.setCellValueFactory(data -> data.getValue().startTimeProperty());
            endCol.setCellValueFactory(data -> data.getValue().endTimeProperty());
            roomNoCol.setCellValueFactory(data -> data.getValue().roomNoProperty());

            examScheduleTable.setItems(observableSchedules);
        } catch (Exception e) {
            showError("Error loading exam schedules: " + e.getMessage());
        }
    }

    private String getDayFromDatePicker() {
        if (datePicker.getValue() != null) {
            return datePicker.getValue().getDayOfWeek().toString(); // Example: "MONDAY"
        }
        return null;
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
