module com.example.attendensmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.attendensmanagmentsystem to javafx.fxml;
    exports com.example.attendensmanagmentsystem;
}