package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.ClassScheduleService;
import com.blackcode.sda_project.database.CourseDAO;
import com.blackcode.sda_project.model_entity.ClassSchedule;
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
import java.util.List;

public class AdminClassUIController {

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<ClassSchedule> classScheduleTable;

    @FXML
    private TableColumn<ClassSchedule, String> courseCol;

    @FXML
    private ChoiceBox<String> courseDropdown;

    @FXML
    private TableColumn<ClassSchedule, String> dayCol;

    @FXML
    private ChoiceBox<String> dayDropdown;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField endTimeText;

    @FXML
    private TableColumn<ClassSchedule, String> endtimeCol;

    @FXML
    private TableColumn<ClassSchedule, String> roomnoCol;

    @FXML
    private TextField roomnotext;

    @FXML
    private TableColumn<ClassSchedule, String> starttimeCol;

    @FXML
    private TextField starttimeText;

    private final ClassScheduleService classScheduleService = new ClassScheduleService();

    @FXML
    public void initialize() {
        populateCourses(); // Populate courses dropdown
        populateDays(); // Populate days dropdown
        populateClassScheduleTable(); // Populate TableView
    }

    @FXML
    void onAddClick(ActionEvent event) {
        try {
            String courseName = courseDropdown.getValue();
            String day = dayDropdown.getValue();
            String startTime = starttimeText.getText();
            String endTime = endTimeText.getText();
            String roomNo = roomnotext.getText();

            if (courseName == null || day == null || startTime.isEmpty() || endTime.isEmpty() || roomNo.isEmpty()) {
                showError("All fields are required.");
                return;
            }

            // Get course ID from the course name
            int courseId = CourseDAO.getCourseIdByName(courseName);

            // Add or update the class schedule
            classScheduleService.addOrUpdateClassSchedule(courseId, day, startTime, endTime, roomNo);

            populateClassScheduleTable(); // Refresh TableView
            showSuccess("Class schedule added or updated successfully!");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Error adding class schedule: " + e.getMessage());
        }
    }

    @FXML
    void onDeleteClick(ActionEvent event) {
        try {
            // Confirm before deleting all class schedules
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setContentText("Are you sure you want to delete all class schedules?");
            if (confirmation.showAndWait().get() != ButtonType.OK) {
                return; // Exit if user cancels
            }

            // Delete all schedules via service
            classScheduleService.removeAllClassSchedules();

            populateClassScheduleTable(); // Refresh TableView
            showSuccess("All class schedules removed successfully!");
        } catch (Exception e) {
            showError("Error deleting class schedules: " + e.getMessage());
        }
    }

    @FXML
    void onBackClick(ActionEvent event) {
        try {
            backButton.getScene().getWindow().hide();
            Stage dashboard = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/admin-dashboard.fxml"));
            Scene scene = new Scene(root);
            dashboard.setScene(scene);
            dashboard.show();
        } catch (IOException e) {
            showError("Error navigating back: " + e.getMessage());
        }
    }

    private void populateCourses() {
        try {
            List<String> courseNames = CourseDAO.getAllCourseNames();
            courseDropdown.getItems().addAll(courseNames);
        } catch (Exception e) {
            showError("Error fetching courses: " + e.getMessage());
        }
    }

    private void populateDays() {
        // Populate the days dropdown with static values
        dayDropdown.getItems().addAll(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
    }

    private void populateClassScheduleTable() {
        try {
            // Fetch all schedules from the database
            List<ClassSchedule> classSchedules = classScheduleService.getAllClassSchedules();
            ObservableList<ClassSchedule> observableSchedules = FXCollections.observableArrayList(classSchedules);

            // Bind TableView columns to ClassSchedule properties
            courseCol.setCellValueFactory(data -> data.getValue().courseProperty());
            dayCol.setCellValueFactory(data -> data.getValue().dayProperty());
            starttimeCol.setCellValueFactory(data -> data.getValue().startTimeProperty());
            endtimeCol.setCellValueFactory(data -> data.getValue().endTimeProperty());
            roomnoCol.setCellValueFactory(data -> data.getValue().roomNoProperty());

            // Set items in the TableView
            classScheduleTable.setItems(observableSchedules);
        } catch (Exception e) {
            showError("Error loading class schedules: " + e.getMessage());
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
