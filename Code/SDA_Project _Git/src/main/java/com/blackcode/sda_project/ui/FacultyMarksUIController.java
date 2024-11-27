package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.MarksService;
import com.blackcode.sda_project.business.CourseService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Course;
import com.blackcode.sda_project.model_entity.Marks;
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
import java.util.List;

public class FacultyMarksUIController {

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<Course> courseDropdown;

    @FXML
    private TextField obtainedText;

    @FXML
    private ChoiceBox<Student> studentDropdown;

    @FXML
    private TextField totalText;

    @FXML
    private TextField typeText;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<Marks> marksTable;

    @FXML
    private TableColumn<Marks, String> studentCol;

    @FXML
    private TableColumn<Marks, String> typeCol;

    @FXML
    private TableColumn<Marks, Integer> obtainedCol;

    @FXML
    private TableColumn<Marks, Integer> totalCol;

    private final MarksService marksService = new MarksService();
    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        populateCourseDropdown(); // Populate courses
        courseDropdown.setOnAction(event -> {
            populateStudentDropdown(); // Populate students
            populateMarksTable(); // Populate marks table
        });
    }


    private void populateCourseDropdown() {
        try {
            int facultyId = SessionManager.getInstance().getUserId();
            List<Course> courses = courseService.getCoursesByFacultyId(facultyId);
            courseDropdown.setItems(FXCollections.observableArrayList(courses));
        } catch (Exception e) {
            showError("Error loading courses: " + e.getMessage());
        }
    }

    private void populateStudentDropdown() {
        try {
            Course selectedCourse = courseDropdown.getValue();

            if (selectedCourse == null) {
                System.out.println("No course selected."); // Debug log
                studentDropdown.getItems().clear();
                return;
            }

            System.out.println("Selected Course: " + selectedCourse.getCourseId()); // Debug log

            List<Student> students = marksService.getStudentsByCourse(selectedCourse.getCourseId());
            System.out.println("Fetched Students: " + students); // Debug log

            ObservableList<Student> studentList = FXCollections.observableArrayList(students);
            studentDropdown.setItems(studentList);
        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage()); // Debug log
            showError("Error loading students: " + e.getMessage());
        }
    }



    private void populateMarksTable() {
        try {
            Course selectedCourse = courseDropdown.getValue();

            if (selectedCourse == null) {
                marksTable.getItems().clear();
                return;
            }

            // Fetch marks for the selected course
            List<Marks> marksList = marksService.getMarksForCourse(selectedCourse.getCourseId());
            ObservableList<Marks> observableMarksList = FXCollections.observableArrayList(marksList);

            // Bind table columns to Marks properties
            studentCol.setCellValueFactory(data -> data.getValue().studentNameProperty());
            typeCol.setCellValueFactory(data -> data.getValue().typeProperty());
            obtainedCol.setCellValueFactory(data -> data.getValue().obtainedMarksProperty().asObject());
            totalCol.setCellValueFactory(data -> data.getValue().totalMarksProperty().asObject());

            marksTable.setItems(observableMarksList);
        } catch (Exception e) {
            showError("Error loading marks: " + e.getMessage());
        }
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
    void onUpdateClick(ActionEvent event) {
        try {
            Course selectedCourse = courseDropdown.getValue();
            Student selectedStudent = studentDropdown.getValue();
            String type = typeText.getText();
            int obtainedMarks = Integer.parseInt(obtainedText.getText());
            int totalMarks = Integer.parseInt(totalText.getText());

            if (selectedCourse == null || selectedStudent == null || type.isEmpty() || obtainedMarks < 0 || totalMarks <= 0) {
                showError("Please fill all fields with valid data.");
                return;
            }

            marksService.addOrUpdateMarks(
                    selectedCourse.getCourseId(),
                    selectedStudent.getStudentId(),
                    type,
                    obtainedMarks,
                    totalMarks
            );

            showSuccess("Marks updated successfully!");

            // Refresh the marks table
            populateMarksTable();

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values for marks.");
        } catch (Exception e) {
            showError("Error updating marks: " + e.getMessage());
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
