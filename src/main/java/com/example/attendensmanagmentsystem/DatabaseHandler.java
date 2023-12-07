package com.example.attendensmanagmentsystem;
import java.sql.ResultSet;


 import java.sql.*;
import java.time.LocalDate;
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


    public void signUpUser(String FirstName, String LastName, String username,
                           String password, String role)
            throws SQLException, ClassNotFoundException {

        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_ROLE + ")" +
                "VALUES(?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, FirstName);
            prSt.setString(2, LastName);
            prSt.setString(3, username);
            prSt.setString(4, password);
            prSt.setString(5, role);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username, String password) {
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(select)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt(Const.USERS_ID);  // Use the correct column name
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

        String query = "SELECT * FROM students"; // Ваш SQL-запрос для извлечения данных

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


    public List<LocalDate> getAttendanceDates() {
        List<LocalDate> dates = new ArrayList<>();

        try {
            // Пример SQL-запроса для выборки дат посещаемости
            String selectQuery = "SELECT DISTINCT date_column FROM your_table_name";

            // Замените "your_table_name" на ваше имя таблицы и "date_column" на имя колонки с датами

            // Получаем соединение с базой данных
            Connection connection = getDbConnection(); // ваш код получения соединения

            // Подготавливаем запрос
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Выполняем запрос
                ResultSet resultSet = preparedStatement.executeQuery();

                // Извлекаем даты из результата запроса
                while (resultSet.next()) {
                    // Используем метод getDate для получения значения типа java.sql.Date
                    java.sql.Date sqlDate = resultSet.getDate("date_column");

                    // Преобразуем его в LocalDate
                    LocalDate date = sqlDate.toLocalDate();

                    dates.add(date);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Лучше обработать исключение в соответствии с вашими потребностями
        }

        return dates;
    }


    public void updateAttendanceOnDate(String selectedDate) {
        // Проверяем, выбрана ли дата
        if (selectedDate != null && !selectedDate.isEmpty()) {
            try {
                // Пример SQL-запроса для обновления посещаемости
                String updateQuery = "UPDATE your_table_name SET attendance = ? WHERE date_column = ?";

                // Замените "your_table_name" на ваше имя таблицы и "date_column" на имя колонки с датами

                // Получаем соединение с базой данных
                Connection connection = getDbConnection(); // ваш код получения соединения

                // Подготавливаем запрос
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    // Устанавливаем параметры запроса
                    preparedStatement.setString(1, "Present"); // Например, обозначение присутствия
                    preparedStatement.setString(2, selectedDate);

                    // Выполняем запрос
                    preparedStatement.executeUpdate();

                    // Здесь можно добавить обработку успешного обновления
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace(); // Лучше обработать исключение в соответствии с вашими потребностями
            }
        } else {
            System.out.println("Дата не выбрана");
            // Здесь можно добавить дополнительную логику или сообщение об ошибке
        }
    }


    public void updateStudentAttendance(Student student, LocalDate date, String attendanceUpdate) {
        try {
            // Ваш SQL-запрос для обновления посещаемости
            String updateQuery = "UPDATE students SET " + dateColumnForUpdate(date) + " = ? WHERE id = ?";

            // Получите соединение с базой данных
            Connection connection = getDbConnection(); // ваш код получения соединения

            // Подготовьте запрос
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                // Установите параметры запроса
                preparedStatement.setString(1, attendanceUpdate);
                preparedStatement.setInt(2, student.getId()); // Используйте метод getId(), который у вас есть в классе Student

                // Выполните запрос
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Обработайте исключения соответствующим образом
        }
    }

    // Метод для определения соответствующей колонки по дате
    private String dateColumnForUpdate(LocalDate date) {
        switch (date.getDayOfMonth()) {
            case 1:
                return "first_date";
            case 2:
                return "second_date";
            case 3:
                return "third_date";
            case 4:
                return "fourth_date";
            case 5:
                return "fifth_date";
            default:
                throw new IllegalArgumentException("Недопустимая дата: " + date);
        }
    }

    public boolean deleteStudent(int studentId) {
        // Create an instance of DatabaseHandler
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Use try-with-resources to automatically close the resources
        try (Connection connection = databaseHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?")) {

            preparedStatement.setInt(1, studentId);

            // Execute SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            // If deletion is successful (at least one row affected), return true
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exceptions (consider logging instead of printing)
            return false;
        }
    }

    public boolean addStudent(Student student) {
        String insert = "INSERT INTO students (group_name, full_name, gpa) VALUES (?, ?, ?)";

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(insert)) {
            prSt.setString(1, student.getGroup());
            prSt.setString(2, student.getFullName());
            prSt.setDouble(3, student.getGpa());

            prSt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Обработайте исключение соответствующим образом
            return false;
        }
    }

    public DatabaseHandler() {
        // ваш код инициализации
    }

}





