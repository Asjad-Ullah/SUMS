package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.database.ExamScheduleDAO;
import com.blackcode.sda_project.model_entity.ExamSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class StudentExamUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<ExamSchedule, String> courseCol;

    @FXML
    private TableColumn<ExamSchedule, String> dateCol;

    @FXML
    private TableColumn<ExamSchedule, String> dayCol;

    @FXML
    private TableColumn<ExamSchedule, String> endCol;

    @FXML
    private TableView<ExamSchedule> examTable;

    @FXML
    private TableColumn<ExamSchedule, String> roomCol;

    @FXML
    private TableColumn<ExamSchedule, String> startCol;

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        backButton.getScene().getWindow().hide();
        Stage dashboard = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/blackcode/sda_project/student-dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboard.setScene(scene);
        dashboard.show();
    }

    @FXML
    public void initialize() {
        try {
            initializeTableColumns();
            loadExamSchedules();
        } catch (Exception e) {
            showError("Error loading exam schedules: " + e.getMessage());
        }
    }

    private void initializeTableColumns() {
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
    }

    private void loadExamSchedules() {
        try {
            List<ExamSchedule> schedules = ExamScheduleDAO.getAllExamSchedules();
            ObservableList<ExamSchedule> observableSchedules = FXCollections.observableArrayList(schedules);
            examTable.setItems(observableSchedules);
        } catch (Exception e) {
            showError("Error loading exam schedules: " + e.getMessage());
        }
    }

    private void showError(String message) {
        System.err.println(message); // Debug log
    }
}
