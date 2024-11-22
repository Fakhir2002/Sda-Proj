package com.example.temp.DB_HANDLER;

import com.example.proj.Patient;

import java.sql.*;
import java.time.LocalDate;

public class Patient_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL queries
    private static final String INSERT_PATIENT_QUERY =
            "INSERT INTO patients (first_name, last_name, contact_no, dob, address, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String LOGIN_QUERY =
            "SELECT * FROM patients WHERE username = ? AND password = ?";
    private static final String GET_PATIENT_DETAILS_QUERY =
            "SELECT id, first_name, last_name, contact_no, dob, address, username, password FROM patients WHERE username = ?";
    private static final String GET_USERNAME_QUERY =
            "SELECT username FROM patients WHERE username = ? AND password = ?";

    /**
     * Saves patient data to the database.
     */
    public boolean registerPatient(String firstName, String lastName, String contactNo,
                                   String dob, String address, String username, String password) {
        System.out.println("registerPatient method called with parameters:");
        System.out.println("First Name: " + firstName + ", Last Name: " + lastName + ", Contact No: " + contactNo +
                ", DOB: " + dob + ", Address: " + address + ", Username: " + username + ", Password: " + password);
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
            System.out.println("Number of rows inserted: " + rowsInserted);
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the patient's login credentials.
     */
    public boolean validateLogin(String username, String password) {
        System.out.println("validateLogin method called with Username: " + username + " and Password: " + password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean isValid = resultSet.next(); // Check if a record was found
                System.out.println("Login validation result: " + isValid);
                return isValid;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all patient details by username to create a Patient object.
     */
    public Patient getPatientDetails(String username) {
        System.out.println("getPatientDetails method called for Username: " + username);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PATIENT_DETAILS_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String contactNo = resultSet.getString("contact_no");
                    String dob = resultSet.getString("dob");  // Using LocalDate here
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");

                    // Print all patient details to the console
                    System.out.println("Patient Details:");
                    System.out.println("ID: " + id);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Contact No: " + contactNo);
                    System.out.println("Date of Birth: " + dob);
                    System.out.println("Address: " + address);
                    System.out.println("Username: " + username);  // Assuming username is also a field to print
                    System.out.println("Password: " + password);

                    // Create and return a Patient object
                    return new Patient(id, firstName, lastName, contactNo, dob, address, username, password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching patient is found
    }

    /**
     * Retrieves patient's username based on username and password.
     */
    public String getPatientUsername(String username, String password) {
        System.out.println("getPatientUsername method called with Username: " + username + " and Password: " + password);
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERNAME_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if a record was found
                if (resultSet.next()) {
                    String user = resultSet.getString("username");
                    System.out.println("Retrieved Username: " + user);
                    return user;
                }
            }

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
        }
        return null; // Return null if no matching username is found
    }
}
