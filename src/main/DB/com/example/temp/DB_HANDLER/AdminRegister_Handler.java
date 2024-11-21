package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminRegister_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Replace 'curetrack' with your database name
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting admin data
    private static final String INSERT_ADMIN_QUERY =
            "INSERT INTO admin (first_name, last_name, contact_no, dob, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves admin data to the database.
     *
     * @param firstName  Admin's first name
     * @param lastName   Admin's last name
     * @param contactNo  Admin's contact number
     * @param dob        Admin's date of birth (in 'YYYY-MM-DD' format)
     * @param address    Admin's address
     * @param username   Admin's username
     * @param password   Admin's password (consider hashing for security)
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerAdmin(String firstName, String lastName, String contactNo, String dob,
                                 String address, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password); // Consider encrypting/hashing the password

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
