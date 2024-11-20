package com.example.temp.DB_HANDLER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientRegister_Handler {


    // Database credentials
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting patient data
    private static final String INSERT_PATIENT_QUERY =
            "INSERT INTO users (first_name, last_name, contact_no, dob, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Saves patient data to the database.
     *
     * @param firstName  Patient's first name
     * @param lastName   Patient's last name
     * @param contactNo  Patient's contact number
     * @param dob        Patient's date of birth
     * @param address    Patient's address
     * @param username   Patient's username
     * @param password   Patient's password
     * @return true if the data was successfully inserted, false otherwise
     */
    public boolean registerPatient(String firstName, String lastName, String contactNo,
                                   String dob, String address, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNo);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password); // Consider hashing the password
           // preparedStatement.setString(8, "Patient");

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
