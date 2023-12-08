package com.example.attendensmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private MenuButton menuButton;

    @FXML
    private void handleMenuItem(ActionEvent event) {
        MenuItem source = (MenuItem) event.getSource();
        String selectedRole = source.getText();
        menuButton.setText(selectedRole);
    }


    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label resultLabel;

    private DatabaseHandler dbHandler;

    @FXML
    void initialize() {
        dbHandler = new DatabaseHandler();
        submitButton.setOnAction(actionEvent -> {
            String usernameText = usernameTextField.getText().trim();
            String passwordText = passwordField.getText().trim();

            if (!usernameText.equals("") && !passwordText.equals(""))
                loginUser(usernameText, passwordText);
            else
                System.out.println("Error");
        });
    }

    private void loginUser(String usernameText, String passwordText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = dbHandler.getUser(usernameText, passwordText);

        if (user != null) {
            System.out.println("Authorization was successful!");
            String role = getUserRoleFromDatabase(usernameText);
            openWindowByRole(role);
        } else {
            System.out.println("Incorrect login or password!");
        }
    }
    private String getUserRoleFromDatabase(String username) {
        String role = null;

        try {
            Connection connection = dbHandler.getDbConnection();
            String query = "SELECT role FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        role = resultSet.getString(Const.USERS_ROLE);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return role;
    }


    private void openWindowByRole(String role) {
        try {
            if (role != null) {
                FXMLLoader loader = new FXMLLoader();

                switch (role) {
                    case "Admin":
                        loader.setLocation(getClass().getResource("adminWindow.fxml"));
                        break;
                    case "Teacher":
                        loader.setLocation(getClass().getResource("teacherWindow.fxml"));
                        break;
                    case "Student":
                        loader.setLocation(getClass().getResource("studentWindow.fxml"));
                        break;
                    default:
                        System.out.println("Unknown role: " + role);

                        return;
                }

                loader.load();
                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.showAndWait();

            } else {
                System.out.println("Role is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

