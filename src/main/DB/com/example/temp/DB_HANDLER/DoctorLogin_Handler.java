package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorLogin_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for verifying patient login credentials
    private static final String LOGIN_QUERY =
            "SELECT * FROM doctors WHERE username = ? AND password = ?";

    /**
     * Verifies patient login credentials.
     *
     * @param username Patient's username
     * @param password Patient's password (plaintext; consider using hashed comparison)
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if a record was found
                return resultSet.next();
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }
}
