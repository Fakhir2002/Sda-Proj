package com.example.temp.DB_HANDLER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting feedback data with patient_id
    private static final String INSERT_FEEDBACK_QUERY =
            "INSERT INTO feedback (id, patient_name, doctor_name, hospital_name, experience_rating, recommendations, feedback_comments) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Inserts feedback data into the feedback table.
     *
     * @param patientId      Patient's ID
     * @param patientName    Patient's name
     * @param doctorName     Doctor's name
     * @param hospitalName   Hospital's name
     * @param experienceRating Patient's satisfaction (Yes/No)
     * @param recommendations Any recommendations provided by the patient
     * @param feedbackComments Additional comments from the patient
     * @return true if the feedback was successfully inserted, false otherwise
     */
    public boolean insertFeedback(int patientId, String patientName, String doctorName, String hospitalName, boolean experienceRating,
                                  String recommendations, String feedbackComments) {
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FEEDBACK_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, patientId);  // Set patient ID
            preparedStatement.setString(2, patientName);
            preparedStatement.setString(3, doctorName);
            preparedStatement.setString(4, hospitalName);
            preparedStatement.setBoolean(5, experienceRating);  // true for "Yes", false for "No"
            preparedStatement.setString(6, recommendations);
            preparedStatement.setString(7, feedbackComments);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // If rows were affected, the insertion was successful

        } catch (SQLException e) {
            // Log the exception for debugging (use a logger in production)
            e.printStackTrace();
            return false;
        }
    }
}
