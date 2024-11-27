package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.business.MarksService;
import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.model_entity.Marks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class StudentMarksUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Marks, String> courseCol;

    @FXML
    private TableView<Marks> marksTable;

    @FXML
    private TableColumn<Marks, String> obtainedCol;

    @FXML
    private TableColumn<Marks, String> totalCol;

    @FXML
    private TableColumn<Marks, String> typeCol;

    private final MarksService marksService = new MarksService();

    @FXML
    public void initialize() {
        // Initialize table columns with data bindings
        courseCol.setCellValueFactory(data -> data.getValue().courseNameProperty());
        typeCol.setCellValueFactory(data -> data.getValue().typeProperty());
        obtainedCol.setCellValueFactory(data -> data.getValue().obtainedMarksProperty().asString());
        totalCol.setCellValueFactory(data -> data.getValue().totalMarksProperty().asString());

        // Load marks for the logged-in student
        loadStudentMarks();
    }

    private void loadStudentMarks() {
        try {
            int studentId = SessionManager.getInstance().getUserId();

            // Fetch marks for the logged-in student
            List<Marks> marksList = marksService.getMarksByStudent(studentId);
            ObservableList<Marks> observableMarksList = FXCollections.observableArrayList(marksList);

            marksTable.setItems(observableMarksList);
        } catch (Exception e) {
            showError("Error loading marks: " + e.getMessage());
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
