package com.example.temp.DB_HANDLER;

import com.example.proj.Admin;

import java.sql.*;

public class Admin_Handler {

    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private static final String DELETE_DOCTOR_QUERY =
            "DELETE FROM doctors WHERE DoctorID = ?";
    private static final String DELETE_PATIENT_QUERY =
            "DELETE FROM patients WHERE first_name = ?";
    private static final String DELETE_STAFF_QUERY =
            "DELETE FROM staff WHERE first_name = ?";  // Query to delete staff by first_name

    /**
     * Removes a doctor from the database based on their ID.
     *
     * @param doctorID The ID of the doctor to remove.
     * @return true if the doctor was removed successfully, false otherwise.
     */
    public static boolean removeDoctor(int doctorID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DOCTOR_QUERY)) {

            preparedStatement.setInt(1, doctorID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a patient from the database based on their name.
     *
     * @param patientName The name of the patient to remove.
     * @return true if the patient was removed successfully, false otherwise.
     */
    public boolean removePatient(String patientName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PATIENT_QUERY)) {

            preparedStatement.setString(1, patientName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a staff member from the database based on their name.
     *
     * @param staffName The name of the staff member to remove.
     * @return true if the staff member was removed successfully, false otherwise.
     */
    public boolean removeStaff(String staffName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_QUERY)) {

            preparedStatement.setString(1, staffName);  // Using staff name to remove
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Retrieves the details of an admin from the database based on their username.
     *
     * @param username The username of the admin.
     * @return An Admin object containing the admin's details, or null if not found.
     */
    public Admin getAdminDetails(String username) {
        String query = "SELECT * FROM admins WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFirstName(resultSet.getString("firstName"));
                admin.setLastName(resultSet.getString("lastName"));
                admin.setContactNo(resultSet.getString("contactNo"));
                admin.setDob(resultSet.getString("dob"));
                admin.setAddress(resultSet.getString("address"));
                return admin;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean validateLogin(String username, String password) {
        return true;
    }
}
