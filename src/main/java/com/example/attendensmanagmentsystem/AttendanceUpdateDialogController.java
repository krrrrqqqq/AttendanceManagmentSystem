package com.example.attendensmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AttendanceUpdateDialogController implements Initializable {
    @FXML
    private TextField firstDateField;
    @FXML
    private TextField secondDateField;
    @FXML
    private TextField thirdDateField;
    @FXML
    private TextField fourthDateField;
    @FXML
    private TextField fifthDateField;
    @FXML
    private TextField attendancePercentageField; // New field for attendance percentage

    private Student student;
    private DatabaseHandler dbHandler;

    public AttendanceUpdateDialogController() {
        this.dbHandler = new DatabaseHandler();
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.dbHandler = new DatabaseHandler();
    }

    private TableView<Student> tableStudents;

    public void setTableStudents(TableView<Student> tableStudents) {
        this.tableStudents = tableStudents;
    }

    private void updateTable() {
        loadStudentsData();
        tableStudents.refresh();
    }

    private void loadStudentsData() {
        tableStudents.getItems().clear();
        List<Student> students = dbHandler.getStudents();
        tableStudents.getItems().addAll(students);
    }

    public void setStudent(Student student) {
        this.student = student;
        firstDateField.setText(student.getFirstDate());
        secondDateField.setText(student.getSecondDate());
        thirdDateField.setText(student.getThirdDate());
        fourthDateField.setText(student.getFourthDate());
        fifthDateField.setText(student.getFifthDate());
        // Set attendance percentage
        attendancePercentageField.setText(String.valueOf(student.getAttendancePercentage()));
    }

    public void updateAttendanceInDatabase() {
        System.out.println("Inside updateAttendanceInDatabase");
        String firstDate = firstDateField.getText();
        String secondDate = secondDateField.getText();
        String thirdDate = thirdDateField.getText();
        String fourthDate = fourthDateField.getText();
        String fifthDate = fifthDateField.getText();
        // Retrieve attendance percentage
        String attendancePercentage = attendancePercentageField.getText();

        dbHandler.updateStudentAttendance(student, firstDate, secondDate, thirdDate, fourthDate, fifthDate, attendancePercentage);
        updateTable();
        System.out.println("Exiting updateAttendanceInDatabase");
    }
}

