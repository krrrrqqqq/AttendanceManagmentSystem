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

    private DatabaseHandler dbHandler; // Добавьте поле для хранения экземпляра DatabaseHandler
    private Runnable updateTableCallback; // Добавьте поле для хранения колбэка обновления таблицы

    // Метод для установки экземпляра DatabaseHandler и колбэка обновления таблицы
    public void setDatabaseHandler(DatabaseHandler dbHandler, Runnable updateTableCallback) {
        this.dbHandler = dbHandler;
        this.updateTableCallback = updateTableCallback;
    }

    // В AddStudentDialogController
    @FXML
    public void addStudentToDatabase() {
        if (dbHandler == null || updateTableCallback == null) {
            System.err.println("DatabaseHandler or updateTableCallback not set");
            return;
        }

        // Получите значения из текстовых полей
        int id = Integer.parseInt(idInput.getText()); // Предполагается, что id вводится как число
        String group = groupNameInput.getText();
        String fullName = fullNameInput.getText();
        double gpa = Double.parseDouble(gpaInput.getText()); // Предполагается, что GPA вводится как число

        // Добавьте студента в базу данных
        dbHandler.addStudent(id, group, fullName, gpa);

        // Закройте диалоговое окно
        Stage stage = (Stage) groupNameInput.getScene().getWindow();
        stage.close();

        // Вызовите колбэк для обновления таблицы
        updateTableCallback.run();
    }

}
