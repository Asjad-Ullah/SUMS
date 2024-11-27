package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.AttendanceService;
import com.blackcode.sda_project.business.CourseService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Attendance;
import com.blackcode.sda_project.model_entity.Course;
import com.blackcode.sda_project.model_entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FacultyAttendanceUIController {

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Attendance, String> courseCol;

    @FXML
    private ChoiceBox<Course> courseDropdown;

    @FXML
    private TableColumn<Attendance, LocalDate> dateCol;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Attendance, String> statusCol;

    @FXML
    private ChoiceBox<String> statusDropdown;

    @FXML
    private TableColumn<Attendance, String> studentCol;

    @FXML
    private ChoiceBox<Student> studentnameDropdown;

    @FXML
    private Button updateButton;

    private final AttendanceService attendanceService = new AttendanceService();
    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        populateCourseDropdown(); // Populate course dropdown
        initializeStatusDropdown(); // Initialize status dropdown

        // Add a listener to the course dropdown to populate the student dropdown dynamically
        courseDropdown.setOnAction(event -> populateStudentDropdown());
    }

    private void populateCourseDropdown() {
        try {
            int facultyId = SessionManager.getInstance().getUserId();
            String facultyName = SessionManager.getInstance().getName();
            System.out.println(facultyId);
            System.out.println(facultyName);
            List<Course> courses = courseService.getCoursesByFacultyId(facultyId); // Fetch all courses
            courseDropdown.setItems(FXCollections.observableArrayList(courses));
        } catch (Exception e) {
            showError("Error loading courses: " + e.getMessage());
        }
    }

    private void initializeStatusDropdown() {
        statusDropdown.setItems(FXCollections.observableArrayList("Present", "Absent", "Late")); // Set status options
    }

    private void populateStudentDropdown() {
        try {
            Course selectedCourse = courseDropdown.getValue();

            if (selectedCourse == null) {
                studentnameDropdown.getItems().clear(); // Clear students if no course is selected
                return;
            }

            // Fetch students enrolled in the selected course
            List<Student> students = attendanceService.getStudentsByCourse(selectedCourse.getCourseId());
            ObservableList<Student> studentList = FXCollections.observableArrayList(students);

            studentnameDropdown.setItems(studentList);
        } catch (Exception e) {
            showError("Error loading students: " + e.getMessage());
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        // Handle back button click

        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/faculty-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    @FXML
    void onUpdateClick(ActionEvent event) {
        try {
            // Fetch selected data
            Course selectedCourse = courseDropdown.getValue();
            Student selectedStudent = studentnameDropdown.getValue();
            String selectedStatus = statusDropdown.getValue();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedCourse == null || selectedStudent == null || selectedStatus == null || selectedDate == null) {
                showError("Please select all required fields before updating.");
                return;
            }

            // Mark attendance
            attendanceService.markAttendance(
                    selectedCourse.getCourseId(),
                    selectedStudent.getStudentId(),
                    selectedDate,
                    selectedStatus
            );

            showSuccess("Attendance updated successfully!");

            // Refresh attendance table
            populateAttendanceTable(selectedCourse.getCourseId(), selectedDate);

        } catch (Exception e) {
            showError("Error updating attendance: " + e.getMessage());
        }
    }

    private void populateAttendanceTable(int courseId, LocalDate date) {
        try {
            List<Attendance> attendanceList = attendanceService.getAttendanceByCourseAndDate(courseId, date);
            ObservableList<Attendance> observableList = FXCollections.observableArrayList(attendanceList);

            // Bind table columns to properties
            studentCol.setCellValueFactory(data -> data.getValue().studentNameProperty());
            courseCol.setCellValueFactory(data -> data.getValue().courseNameProperty());
            dateCol.setCellValueFactory(data -> data.getValue().dateProperty());
            statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

            attendanceTable.setItems(observableList);
        } catch (Exception e) {
            showError("Error loading attendance: " + e.getMessage());
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
