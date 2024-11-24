package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideoConsultationDBHandler {

    // Database connection details (update with your database info)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/user";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345678";

    // Method to insert a new video consultation into the database
    public static boolean saveVideoConsultation(String status, int patientId, int doctorId) {
        String query = "INSERT INTO VideoConsultation (status, patient_id, doctor_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the values for the prepared statement
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, patientId);
            preparedStatement.setInt(3, doctorId);

            // Execute the update and check if it was successful
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Return true if at least one row is inserted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception (e.g., log or rethrow)
            return false; // Return false in case of an error
        }
    }

}
