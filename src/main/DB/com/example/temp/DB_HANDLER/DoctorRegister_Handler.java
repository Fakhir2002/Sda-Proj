package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorRegister_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Replace 'user' with your database name
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting doctor data
    private static final String INSERT_DOCTOR_QUERY =
            "INSERT INTO doctors (name, dob, hospital, specialty, contact, address, username, passwordhash) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves doctor data to the database.
     *
     * @param name      Doctor's full name
     * @param dob       Doctor's date of birth
     * @param hospital  Doctor's affiliated hospital
     * @param specialty Doctor's specialty
     * @param contact   Doctor's contact number
     * @param address   Doctor's address
     * @param username  Doctor's username
     * @param password  Doctor's password
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerDoctor(String name, String dob, String hospital, String specialty,
                                  String contact, String address, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, dob); // Ensure DOB is in 'YYYY-MM-DD' format
            preparedStatement.setString(3, hospital);
            preparedStatement.setString(4, specialty);
            preparedStatement.setString(5, contact);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, username);
            preparedStatement.setString(8, password); // Consider hashing the password for security

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
