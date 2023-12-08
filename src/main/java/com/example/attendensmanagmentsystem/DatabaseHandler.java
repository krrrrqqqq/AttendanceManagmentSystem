package com.example.attendensmanagmentsystem;
import java.sql.ResultSet;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(connectionString,
                dbUsers, dbPass);
        return dbConnection;
    }

    public User getUser(String username, String password) {
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(select)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt(Const.USERS_ID);
                    String userName = resultSet.getString(Const.USERS_USERNAME);
                    String userPassword = resultSet.getString(Const.USERS_PASSWORD);

                    User user = new User(userId, userName, userPassword);
                    return user;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String group = resultSet.getString("group_name");
                String fullName = resultSet.getString("full_name");
                double gpa = resultSet.getDouble("gpa");
                String firstDate = resultSet.getString("first_date");
                String secondDate = resultSet.getString("second_date");
                String thirdDate = resultSet.getString("third_date");
                String fourthDate = resultSet.getString("fourth_date");
                String fifthDate = resultSet.getString("fifth_date");
                double attendancePercentage = resultSet.getDouble("attendance_percentage");

                Student student = new Student(id, group, fullName, gpa, firstDate, secondDate, thirdDate, fourthDate, fifthDate, attendancePercentage);
                students.add(student);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updateStudentAttendance(Student student, String firstDate, String secondDate, String thirdDate, String fourthDate, String fifthDate, String attendancePercentage) {
        try {
            String updateQuery = "UPDATE students SET " +
                    "first_date = ?, " +
                    "second_date = ?, " +
                    "third_date = ?, " +
                    "fourth_date = ?, " +
                    "fifth_date = ?, " +
                    "attendance_percentage = ?::double precision " +
                    "WHERE id = ?";
            Connection connection = getDbConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, firstDate);
                preparedStatement.setString(2, secondDate);
                preparedStatement.setString(3, thirdDate);
                preparedStatement.setString(4, fourthDate);
                preparedStatement.setString(5, fifthDate);
                preparedStatement.setString(6, attendancePercentage); // Set the attendance percentage
                preparedStatement.setInt(7, student.getId());

                preparedStatement.executeUpdate();

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean deleteStudent(int studentId) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        try (Connection connection = databaseHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?")) {
            preparedStatement.setInt(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public interface OnDataChangedListener {
        void onDataChanged();
    }
    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }
    public boolean addStudent(Student student) {
        String insert = "INSERT INTO students (group_name, full_name, gpa) VALUES (?, ?, ?)";

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(insert)) {

            prSt.setString(1, student.getGroup());
            prSt.setString(2, student.getFullName());
            prSt.setDouble(3, student.getGpa());

            int rowsAffected = prSt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student added successfully");

                // Уведомляем слушателей об изменении данных
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
                return true;
            } else {
                System.err.println("Failed to add the student. No rows affected.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to add the student. Exception details: " + e.getMessage());
            return false;
        }
    }
    public DatabaseHandler() {
    }
}





