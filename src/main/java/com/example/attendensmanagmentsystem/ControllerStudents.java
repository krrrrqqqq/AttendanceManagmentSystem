package com.example.attendensmanagmentsystem;
import com.example.attendensmanagmentsystem.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerStudents {

    @FXML
    private Label label;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> fullNameColumn;
    @FXML
    private TableColumn<Student, Integer> javaColumn;
    @FXML
    private TableColumn<Student, Double> javaPercentageColumn;
    @FXML
    private TableColumn<Student, Integer> mathsColumn;
    @FXML
    private TableColumn<Student, Double> mathsPercentageColumn;
    @FXML
    private TableColumn<Student, Integer> totalAttendedColumn;
    @FXML
    private TableColumn<Student, Integer> totalAbsencesColumn;
    @FXML
    private TableColumn<Student, Double> totalPercentageColumn;
    @FXML
    private Button showVisualizationButton;
    @FXML
    private Button exitButton;

    public void initialize() {
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        javaColumn.setCellValueFactory(new PropertyValueFactory<>("java"));
        javaPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("javaPercentage"));
        mathsColumn.setCellValueFactory(new PropertyValueFactory<>("maths"));
        mathsPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("mathsPercentage"));
        totalAttendedColumn.setCellValueFactory(new PropertyValueFactory<>("totalAttended"));
        totalAbsencesColumn.setCellValueFactory(new PropertyValueFactory<>("totalAbsences"));
        totalPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("totalPercentage"));
    }

    @FXML
    private void showVisualization() {
        System.out.println("Show Visualization button clicked");
    }
    @FXML
    private void exit() {
        System.out.println("Exit button clicked");
    }
}