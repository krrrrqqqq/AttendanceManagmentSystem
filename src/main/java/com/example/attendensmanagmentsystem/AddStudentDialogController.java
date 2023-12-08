package com.example.attendensmanagmentsystem;

import com.example.attendensmanagmentsystem.Student;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentDialogController {
    @FXML
    private TextField groupNameInput;
    @FXML
    private TextField idInput;
    @FXML
    private TextField fullNameInput;
    @FXML
    private TextField gpaInput;

    private DatabaseHandler dbHandler;
    private Runnable updateTableCallback;
    public void setDatabaseHandler(DatabaseHandler dbHandler, Runnable updateTableCallback) {
        this.dbHandler = dbHandler;
        this.updateTableCallback = updateTableCallback;
    }
    @FXML
    public void addStudentToDatabase() {
        if (dbHandler == null || updateTableCallback == null) {
            System.err.println("DatabaseHandler or updateTableCallback not set");
            return;
        }
        int id = Integer.parseInt(idInput.getText());
        String group = groupNameInput.getText();
        String fullName = fullNameInput.getText();
        double gpa = Double.parseDouble(gpaInput.getText());

        dbHandler.addStudent(id, group, fullName, gpa);

        Stage stage = (Stage) groupNameInput.getScene().getWindow();
        stage.close();

        updateTableCallback.run();
    }

}
