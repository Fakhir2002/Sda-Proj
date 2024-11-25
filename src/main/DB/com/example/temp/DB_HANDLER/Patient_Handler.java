package com.example.temp.DB_HANDLER;

import com.example.proj.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Patient_Handler implements DatabaseConfig{


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
    private static final String GET_ALL_PATIENTS_QUERY =
            "SELECT id, first_name, last_name, contact_no, dob, address, username, password FROM patients";
    private static final String GET_USERNAME_BY_NAME_QUERY =
            "SELECT username FROM patients WHERE CONCAT(first_name, ' ', last_name) = ?";


    /**
     * Saves patient data to the database.
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
            preparedStatement.setString(7, password);

            // Execute the query
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates the patient's login credentials.
     */
    public static boolean validateLogin(String username, String password) {
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
     */
    public Patient getPatientDetails(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PATIENT_DETAILS_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Patient(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("contact_no"),
                            resultSet.getString("dob"),
                            resultSet.getString("address"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
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
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERNAME_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching username is found
    }

    /**
     * Retrieves all patients from the database.
     */
    public List<Patient> getAllPatientDetails() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PATIENTS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                patients.add(new Patient(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("contact_no"),
                        resultSet.getString("dob"),
                        resultSet.getString("address"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public String getPatientNameById(int patientId) {
        String query = "SELECT first_name, last_name FROM patients WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the patient ID for the prepared statement
            preparedStatement.setInt(1, patientId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Construct the full name from the first and last name
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    return firstName + " " + lastName; // Return the full name
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching patient is found
    }

    public String getPatientUsernameByName(String selectedPatientName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERNAME_BY_NAME_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, selectedPatientName);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching username is found
    }
}
