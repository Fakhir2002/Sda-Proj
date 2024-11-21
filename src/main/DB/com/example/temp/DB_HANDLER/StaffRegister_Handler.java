package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffRegister_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Replace 'user' with your actual database name
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting staff data
    private static final String INSERT_STAFF_QUERY =
            "INSERT INTO staff (first_name, last_name, contact_no, dob, address, username, passwordhash) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves staff data to the database.
     *
     * @param firstName     Staff's first name
     * @param lastName      Staff's last name
     * @param contactNo     Staff's contact number
     * @param dob           Staff's date of birth (ensure 'YYYY-MM-DD' format)
     * @param address       Staff's address
     * @param username      Staff's username
     * @param passwordHash  Staff's hashed password
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerStaff(String firstName, String lastName, String contactNo, String dob,
                                 String address, String username, String passwordHash) {
        // Establish the database connection and execute the query inside try-with-resources for automatic resource management
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob); // Ensure DOB is in 'YYYY-MM-DD' format
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, passwordHash); // Store the hashed password for security

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            // Return true if at least one row was inserted, otherwise false
            return rowsInserted > 0;

        } catch (SQLException e) {
            // Log the exception (you could also use a logging framework like Log4j here)
            e.printStackTrace();
            return false; // Return false if an error occurs during insertion
        }
    }
}
