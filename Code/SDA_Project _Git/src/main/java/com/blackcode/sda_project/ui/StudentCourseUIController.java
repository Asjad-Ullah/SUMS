package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.CourseEnrollmentService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Course;
import com.blackcode.sda_project.model_entity.StudentCourseEnrollment;
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

public class StudentCourseUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<StudentCourseEnrollment, String> codeCol;

    @FXML
    private TableColumn<StudentCourseEnrollment, String> courseCol;

    @FXML
    private ChoiceBox<Course> courseDropdown;

    @FXML
    private TableView<StudentCourseEnrollment> courseTable;

    @FXML
    private TableColumn<StudentCourseEnrollment, Integer> credithourCol;

    @FXML
    private TableColumn<StudentCourseEnrollment, String> descriptionCol;

    @FXML
    private Button enrollButton;

    private final CourseEnrollmentService courseEnrollmentService = new CourseEnrollmentService(); // Business layer

    @FXML
    public void initialize() {
        populateEligibleCourses(); // Populate dropdown with eligible courses
        populateEnrolledCourses(); // Populate table with enrolled courses
    }

    private void populateEligibleCourses() {
        try {
            int studentId = SessionManager.getInstance().getUserId(); // Get logged-in student ID
            List<Course> eligibleCourses = courseEnrollmentService.getEligibleCourses(studentId);
            courseDropdown.setItems(FXCollections.observableArrayList(eligibleCourses));
        } catch (Exception e) {
            showError("Error fetching eligible courses: " + e.getMessage());
        }
    }

    private void populateEnrolledCourses() {
        try {
            int studentId = SessionManager.getInstance().getUserId(); // Get logged-in student ID
            List<StudentCourseEnrollment> enrolledCourses = courseEnrollmentService.getEnrolledCourses(studentId);
            ObservableList<StudentCourseEnrollment> observableCourses = FXCollections.observableArrayList(enrolledCourses);

            // Bind table columns to properties
            courseCol.setCellValueFactory(data -> data.getValue().courseNameProperty());
            codeCol.setCellValueFactory(data -> data.getValue().courseCodeProperty());
            credithourCol.setCellValueFactory(data -> data.getValue().creditHoursProperty().asObject());
            descriptionCol.setCellValueFactory(data -> data.getValue().descriptionProperty());

            courseTable.setItems(observableCourses);
        } catch (Exception e) {
            showError("Error fetching enrolled courses: " + e.getMessage());
        }
    }

    @FXML
    void onEnrollClick(ActionEvent event) {
        try {
            Course selectedCourse = courseDropdown.getValue();

            if (selectedCourse == null) {
                showError("Please select a course to enroll.");
                return;
            }

            int studentId = SessionManager.getInstance().getUserId(); // Get logged-in student ID
            courseEnrollmentService.enrollCourse(studentId, selectedCourse.getCourseId());

            showSuccess("Course enrolled successfully!");
            populateEligibleCourses(); // Refresh dropdown
            populateEnrolledCourses(); // Refresh table
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Error enrolling course: " + e.getMessage());
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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
