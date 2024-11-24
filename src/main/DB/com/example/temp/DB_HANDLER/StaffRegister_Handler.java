package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffRegister_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting staff data
    private static final String INSERT_STAFF_QUERY =
            "INSERT INTO staff (first_name, last_name, contact_no, dob, address, username, password,hospital) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves staff data to the database.
     *
     * @param firstName        Staff's first name
     * @param lastName         Staff's last name
     * @param contactNo        Staff's contact number
     * @param dob              Staff's date of birth
     * @param address          Staff's address
     * @param username         Staff's username
     * @param password         Staff's password
     * @param selectedHospital
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerStaff(String firstName, String lastName, String contactNo,
                                 String dob, String address, String username, String password, String selectedHospital) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STAFF_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, selectedHospital); // Consider hashing the password

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
