package com.example.attendensmanagmentsystem;

import com.example.attendensmanagmentsystem.Student;


import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class AddStudentDialogController {
    @FXML
    private TextField groupNameInput;
    @FXML
    private TextField fullNameInput;
    @FXML
    private TextField gpaInput;

    private Dialog<ButtonType> dialog;

    private ControllerTeacher parentController;

    public void setParentController(ControllerTeacher parentController) {
        this.parentController = parentController;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private void handleOK() {
        // Ваш код для обработки введенных данных и добавления студента
        // groupNameInput.getText(), fullNameInput.getText(), gpaInput.getText()

        // Закрываем диалоговое окно
        if (dialog != null) {
            dialog.setResult(ButtonType.OK);
            dialog.close();
        }

        // Вызываем метод добавления студента в родительском контроллере
        if (parentController != null) {
            parentController.handleAddStudent(groupNameInput.getText(), fullNameInput.getText(), Double.parseDouble(gpaInput.getText()));

            // Обновляем таблицу в родительском контроллере
            parentController.updateTable();
        }
    }

    @FXML
    private void handleCancel() {
        // Закрываем диалоговое окно
        if (dialog != null) {
            dialog.setResult(ButtonType.CANCEL);
            dialog.close();
        }
    }
}
