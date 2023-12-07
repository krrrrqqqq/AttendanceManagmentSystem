package com.example.attendensmanagmentsystem;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class ControllerTeacher {
    @FXML
    private TableView<Student> tableStudents;
    @FXML
    private TableColumn<Student, String> group;
    @FXML
    private TableColumn<Student, Integer> id;
    @FXML
    private TableColumn<Student, String> fullName;
    @FXML
    private TableColumn<Student, Double> gpa;
    @FXML
    private TableColumn<Student, LocalDate> firstDate;
    @FXML
    private TableColumn<Student, LocalDate> secondDate;
    @FXML
    private TableColumn<Student, LocalDate> thirdDate;
    @FXML
    private TableColumn<Student, LocalDate> fourthDate;
    @FXML
    private TableColumn<Student, LocalDate> fifthDate;
    @FXML
    private TableColumn<Student, Double> attendancePercentage;

        @FXML
        private Button updateAttendance;
        @FXML
        private Button addStudent;
        @FXML
        private Button deleteStudent;
        @FXML
        private Button Exit;
        @FXML
        private MenuButton filterBy;
        @FXML
        private DatabaseHandler dbHandler;
        @FXML
        public void exitApplication() {
            // Создаем диалоговое окно подтверждения
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Exit Application");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Are you sure you want to exit the application?");

            // Устанавливаем кнопки "Yes" и "No"
            confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Отображаем диалоговое окно и ждем результата
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Если выбрана кнопка "Yes", закрываем приложение
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Stage stage = (Stage) updateAttendance.getScene().getWindow();
                stage.close();
                Platform.exit();
            }
            // В противном случае, ничего не делаем (пользователь выбрал "No")
        }
        @FXML
        public void deleteStudent(ActionEvent event) {
        // Получаем выбранного студента из таблицы
        Student selectedStudent = getSelectedStudent();

        // Проверяем, выбран ли студент
        if (selectedStudent != null) {
            // Создаем диалоговое окно подтверждения
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Delete Student");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Are you sure you want to delete this student?");

            // Устанавливаем кнопки "Yes" и "No"
            confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Отображаем диалоговое окно и ждем результата
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Если выбрана кнопка "Yes", удаляем студента
            if (result.isPresent() && result.get() == ButtonType.YES) {
                // Удаляем студента из базы данных
                if (dbHandler.deleteStudent(selectedStudent.getId())) {
                    // Если удаление успешно, обновляем таблицу
                    loadStudentsData();
                } else {
                    showAlert("Error", "Failed to delete the student.");
                }
            }
        } else {
            showAlert("No Student Selected", "Please select a student to delete.");
        }
    }














    public void initialize() {
        dbHandler = new DatabaseHandler();

        // Инициализация столбцов таблицы
        group.setCellValueFactory(new PropertyValueFactory<>("Group"));
        id.setCellValueFactory(new PropertyValueFactory<>("ID")); // Предполагается, что id - это идентификатор студента
        fullName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        firstDate.setCellValueFactory(new PropertyValueFactory<>("firstDate"));
        secondDate.setCellValueFactory(new PropertyValueFactory<>("secondDate"));
        thirdDate.setCellValueFactory(new PropertyValueFactory<>("thirdDate"));
        fourthDate.setCellValueFactory(new PropertyValueFactory<>("fourthDate"));
        fifthDate.setCellValueFactory(new PropertyValueFactory<>("fifthDate"));
        gpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        attendancePercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));

        // Загрузка данных в таблицу
        loadStudentsData();
    }

    private void loadStudentsData() {
        // Очищаем таблицу перед загрузкой новых данных
        tableStudents.getItems().clear();

        // Получаем данные из базы данных
        List<Student> students = dbHandler.getStudents();

        // Загружаем данные в таблицу
        tableStudents.getItems().addAll(students);
    }








    public void initializeTableView() {
        TableColumn<Student, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fName")); // Используйте поле fName, так как у вас нет full_name

        TableColumn<Student, Double> attendanceColumn = new TableColumn<>("Attendance Percentage");
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));

        // Добавьте столбцы к вашему TableView
        tableStudents.getColumns().addAll(fullNameColumn, attendanceColumn);
    }
    private int getAttendanceByDate(Student student, String dateColumn) {
        // В зависимости от переданной даты возвращает соответствующую посещаемость
        switch (dateColumn) {
            case "firstDate":
                return student.getFirstDateAttendance();
            // Повторите для остальных дат
            default:
                return 0;

        }
    }
    @FXML
    public void updateAttendanceOnDate(String selectedDate) {
        // Создаем экземпляр DatabaseHandler
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Проверяем, выбрана ли дата
        if (selectedDate != null && !selectedDate.isEmpty()) {
            // Ваш остальной код ...
            // Здесь вызывайте ваш метод обновления посещаемости с выбранной датой
            databaseHandler.updateAttendanceOnDate(selectedDate);
            // Дополнительные действия, если необходимо
        } else {
            System.out.println("Дата не выбрана");
            // Здесь можно добавить дополнительную логику или сообщение об ошибке
        }
    }

    private void showAlert(String title, String content) {
        // Метод для отображения сообщения
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private Student getSelectedStudent() {
        // Получаем выбранного студента из таблицы
        return tableStudents.getSelectionModel().getSelectedItem();
    }
    @FXML
    private TextField groupNameInput;
    @FXML
    private TextField fullNameInput;
    @FXML
    private TextField gpaInput;
    @FXML
    private TextField firstDateInput;
    @FXML
    private TextField secondDateInput;
    @FXML
    private TextField thirdDateInput;
    @FXML
    private TextField fourthDateInput;
    @FXML
    private TextField fifthDateInput;



    @FXML
    public void addStudent() {
        openAddStudentDialog();
    }
    @FXML
    public void openAddStudentDialog() {
        try {
            // Загружаем fxml для диалогового окна добавления студента
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Создаем диалоговое окно
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setDialogPane(dialogPane);

            // Получаем контроллер диалогового окна
            AddStudentDialogController dialogController = loader.getController();

            // Инициализируем диалог в контроллере
            dialogController.setDialog(dialog);

            // Устанавливаем родительский контроллер
            dialogController.setParentController(this);

            // Отображаем диалоговое окно и ждем результата
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления данных в таблице
    public void updateTable() {
        loadStudentsData();
    }

        public void handleAddStudent(String groupName, String fullName, double gpa){
            // Создаем нового студента
            Student newStudent = new Student(0, groupName, fullName, gpa, "", "", "", "", "", 0);

            // Добавляем студента в базу данных
            if (dbHandler.addStudent(newStudent)) {
                // Если добавление успешно, обновляем таблицу
                loadStudentsData();
                System.out.println("New student added");
            } else {
                showAlert("Error", "Failed to add the student. Check console for details.");
            }
        }

    @FXML
    public void filter() {
        // Реализуйте логику фильтрации
    }
}



