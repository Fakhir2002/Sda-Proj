package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffLogin_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for validating staff login
    private static final String VALIDATE_STAFF_QUERY =
            "SELECT COUNT(*) FROM staff WHERE username = ? AND password = ?";

    /**
     * Verifies staff credentials for login.
     *
     * @param username Staff's username
     * @param password Staff's password
     * @return true if the credentials are valid, false otherwise
     */
    public boolean loginStaff(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_STAFF_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Return true if the count is greater than 0
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if login fails
    }
}
