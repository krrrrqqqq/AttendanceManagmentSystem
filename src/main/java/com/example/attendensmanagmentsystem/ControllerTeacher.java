package com.example.attendensmanagmentsystem;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
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
    private TextField firstDateAttendance;
    @FXML
    private TextField secondDateAttendance;
    @FXML
    private TextField thirdDateAttendance;
    @FXML
    private TextField fourthDateAttendance;
    @FXML
    private TextField fifthDateAttendance;

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

    public void updateTable() {
        loadStudentsData();
    }




    @FXML
    public void updateAttendance(ActionEvent event) {
        Student selectedStudent = getSelectedStudent();
        if (selectedStudent != null) {
            openAttendanceUpdateDialog(selectedStudent);
        } else {
            showAlert("No Student Selected", "Please select a student to update attendance.");
        }
    }
    private void openAttendanceUpdateDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendanceUpdateDialog.fxml"));
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(tableStudents.getScene().getWindow());
            dialog.setTitle("Update Attendance");
            dialog.setHeaderText("Update Attendance for " + student.getFullName());

            dialog.getDialogPane().setContent(loader.load());

            // Добавьте кнопки применения и отмены к диалоговому окну
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            AttendanceUpdateDialogController controller = loader.getController();
            controller.setStudent(student);
            controller.setTableStudents(tableStudents);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.APPLY) {
                // Здесь код обновления
                controller.updateAttendanceInDatabase();

                // Здесь код обновления таблицы
                loadStudentsData();
                System.out.println("Attendance updated successfully.");

                // Получаем Stage из диалога
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

                // Закрываем диалоговое окно
                stage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


////////////////////


    ////////////////////
    ////////////////////
    ////////////////////
    @FXML
    public void exitApplication() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Exit Application");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to exit this application?");
        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Stage stage = (Stage) updateAttendance.getScene().getWindow();
            stage.close();
            Platform.exit();
        }
    }
    @FXML
    public void deleteStudent(ActionEvent event) {
        Student selectedStudent = getSelectedStudent();
        if (selectedStudent != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Delete Student");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Are you sure you want to delete this student?");
            confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                if (dbHandler.deleteStudent(selectedStudent.getId())) {
                    loadStudentsData();
                } else {
                    showAlert("Error", "Failed to delete the student.");
                }
            }
        } else {
            showAlert("No Student Selected", "Please select a student to delete.");
        }
    }
    ////////////////////
    ////////////////////
    ////////////////////


    public void initialize() {
        dbHandler = new DatabaseHandler();

        // Устанавливаем слушателя изменений данных
        dbHandler.setOnDataChangedListener(this::loadStudentsData);

        // Инициализация столбцов таблицы
        group.setCellValueFactory(new PropertyValueFactory<>("group"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        firstDate.setCellValueFactory(new PropertyValueFactory<>("firstDate"));
        secondDate.setCellValueFactory(new PropertyValueFactory<>("secondDate"));
        thirdDate.setCellValueFactory(new PropertyValueFactory<>("thirdDate"));
        fourthDate.setCellValueFactory(new PropertyValueFactory<>("fourthDate"));
        fifthDate.setCellValueFactory(new PropertyValueFactory<>("fifthDate"));
        gpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        attendancePercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));

        updateTable();
    }


    private void loadStudentsData() {
        tableStudents.getItems().clear();
        List<Student> students = dbHandler.getStudents();
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


    public void handleAddStudent(String groupName, String fullName, double gpa) {
        try {
            // Создаем нового студента
            Student newStudent = new Student(0, groupName, fullName, gpa, "", "", "", "", "", 0);

            // Добавляем студента в базу данных
            if (dbHandler.addStudent(newStudent)) {
                // Если добавление успешно, обновляем таблицу
                loadStudentsData();
                System.out.println("New student added");
            } else {
                showAlert("Error", "Failed to add the student.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid GPA format. Please enter a valid number.");
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error", "Failed to add the student. Check console for details.");
            e.printStackTrace();
        }
    }
}



