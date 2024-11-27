module com.blackcode.sda_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.blackcode.sda_project.model_entity to javafx.base;
    opens com.blackcode.sda_project to javafx.fxml;
    exports com.blackcode.sda_project;
    exports com.blackcode.sda_project.ui;
    opens com.blackcode.sda_project.ui to javafx.fxml;
}
