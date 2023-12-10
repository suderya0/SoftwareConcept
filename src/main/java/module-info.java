module com.example.softwareconcept {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;


    opens com.example.softwareconcept to javafx.fxml;
    exports com.example.softwareconcept;
}