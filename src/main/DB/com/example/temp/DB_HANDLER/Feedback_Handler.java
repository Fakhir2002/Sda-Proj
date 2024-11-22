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

    // SQL query for inserting feedback data
    private static final String INSERT_FEEDBACK_QUERY =
            "INSERT INTO feedback (patient_name, doctor_name, hospital_name, experience_rating, recommendations, feedback_comments) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Inserts feedback data into the feedback table.
     *
     * @param patientName Patient's name
     * @param doctorName  Doctor's name
     * @param hospitalName Hospital's name
     * @param experienceRating Patient's satisfaction (Yes/No)
     * @param recommendations Any recommendations provided by the patient
     * @param feedbackComments Additional comments from the patient
     * @return true if the feedback was successfully inserted, false otherwise
     */
    public boolean insertFeedback(String patientName, String doctorName, String hospitalName, boolean experienceRating,
                                  String recommendations, String feedbackComments) {
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FEEDBACK_QUERY)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, doctorName);
            preparedStatement.setString(3, hospitalName);
            preparedStatement.setBoolean(4, experienceRating);  // true for "Yes", false for "No"
            preparedStatement.setString(5, recommendations);
            preparedStatement.setString(6, feedbackComments);

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
