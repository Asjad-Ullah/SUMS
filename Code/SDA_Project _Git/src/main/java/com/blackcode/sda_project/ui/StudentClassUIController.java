package com.blackcode.sda_project.ui;

import com.blackcode.sda_project.database.ClassScheduleDAO;
import com.blackcode.sda_project.model_entity.ClassSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class StudentClassUIController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<ClassSchedule> classTable;

    @FXML
    private TableColumn<ClassSchedule, String> courseCol;

    @FXML
    private TableColumn<ClassSchedule, String> dayCol;

    @FXML
    private TableColumn<ClassSchedule, String> startCol;

    @FXML
    private TableColumn<ClassSchedule, String> endCol;

    @FXML
    private TableColumn<ClassSchedule, String> roomCol;

    @FXML
    public void initialize() {
        populateClassSchedule(); // Load class schedule on initialization
    }

    private void populateClassSchedule() {
        try {
            List<ClassSchedule> schedules = ClassScheduleDAO.getAllClassSchedules();
            ObservableList<ClassSchedule> scheduleList = FXCollections.observableArrayList(schedules);

            // Bind TableView columns to ClassSchedule properties
            courseCol.setCellValueFactory(data -> data.getValue().courseProperty());
            dayCol.setCellValueFactory(data -> data.getValue().dayProperty());
            startCol.setCellValueFactory(data -> data.getValue().startTimeProperty());
            endCol.setCellValueFactory(data -> data.getValue().endTimeProperty());
            roomCol.setCellValueFactory(data -> data.getValue().roomNoProperty());

            classTable.setItems(scheduleList);
        } catch (Exception e) {
            System.out.println("Error loading class schedule: " + e.getMessage());
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
}
