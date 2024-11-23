package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Appointment_Handler {

    private static final String URL = "jdbc:mysql://localhost:3306/user"; // Replace 'curetrack' with your database name
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public boolean saveAppointment(String status, String date, String time, int doctorId, int patientId) {
        String insertQuery = "INSERT INTO appointment (status, date, time, doctor_id, patient_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set parameters for the query
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            preparedStatement.setInt(4, doctorId);
            preparedStatement.setInt(5, patientId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }
    public boolean deleteAppointment(int appointmentId) {
        String deleteQuery = "DELETE FROM appointment WHERE appointmentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Set the appointmentId parameter for the query
            preparedStatement.setInt(1, appointmentId);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the deletion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }



}
