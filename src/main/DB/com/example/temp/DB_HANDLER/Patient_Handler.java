package com.example.temp.DB_HANDLER;

import com.example.proj.Patient;

import java.sql.*;
import java.time.LocalDate;

public class Patient_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting patient data
    private static final String INSERT_PATIENT_QUERY =
            "INSERT INTO patients (first_name, last_name, contact_no, dob, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // SQL query for verifying patient login credentials
    private static final String LOGIN_QUERY =
            "SELECT * FROM patients WHERE username = ? AND password = ?";

    // SQL query for retrieving all patient details by username
    private static final String GET_PATIENT_DETAILS_QUERY =
            "SELECT first_name, last_name, contact_no, dob, address, username, password FROM patients WHERE username = ?";

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

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the patient's login credentials.
     *
     * @param username Patient's username
     * @param password Patient's password
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Check if a record was found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all patient details by username to create a Patient object.
     *
     * @param username Patient's username
     * @return Patient object with retrieved data, or null if not found
     */
    public Patient getPatientDetails(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PATIENT_DETAILS_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String contactNo = resultSet.getString("contact_no");
                    LocalDate dob = resultSet.getDate("dob").toLocalDate();
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");

                    // Create and return a Patient object
                    return new Patient(firstName, lastName, contactNo, dob, address, username, password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching patient is found
    }
}
