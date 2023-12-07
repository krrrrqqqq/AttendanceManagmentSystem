package com.example.attendensmanagmentsystem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ControllerStudents {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<?> tableView;

    @FXML
    private MenuButton subjectMenuButton;

    @FXML
    private void initialize() {

        for (MenuItem menuItem : subjectMenuButton.getItems()) {
            menuItem.setOnAction(event -> handleSubjectMenuItem(menuItem));
        }
    }

    private void handleSubjectMenuItem(MenuItem selectedItem) {
        String selectedSubject = selectedItem.getText();

        System.out.println("Selected Subject: " + selectedSubject);
    }
}


