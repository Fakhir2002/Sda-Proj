package com.example.temp.DB_HANDLER;
import com.example.proj.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback_Handler {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // SQL query for inserting feedback data with patient_id
    private static final String INSERT_FEEDBACK_QUERY =
            "INSERT INTO feedback (patient_name, doctor_name, hospital_name, experience_rating, recommendations, feedback_comments) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?)";

    // SQL query for retrieving all feedback
    private static final String GET_ALL_FEEDBACK_QUERY = "SELECT * FROM feedback";

    /**
     * Inserts feedback data into the feedback table.
     *

     * @param patientName        Patient's name
     * @param doctorName         Doctor's name
     * @param hospitalName       Hospital's name
     * @param experienceRating   Patient's satisfaction (Yes/No)
     * @param recommendations    Any recommendations provided by the patient
     * @param feedbackComments   Additional comments from the patient
     * @return true if the feedback was successfully inserted, false otherwise
     */
    public boolean insertFeedback( String patientName, String doctorName, String hospitalName, boolean experienceRating,
                                  String recommendations, String feedbackComments) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FEEDBACK_QUERY)) {

            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, doctorName);
            preparedStatement.setString(3, hospitalName);
            preparedStatement.setBoolean(4, experienceRating);  // true for "Yes", false for "No"
            preparedStatement.setString(5, recommendations);
            preparedStatement.setString(6, feedbackComments);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // If rows were affected, the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all feedback entries from the database.
     *
     * @return a list of feedback objects representing all feedback entries.
     */
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FEEDBACK_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through the result set and create Feedback objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patientName = resultSet.getString("patient_name");
                String doctorName = resultSet.getString("doctor_name");
                String hospitalName = resultSet.getString("hospital_name");
                boolean experienceRating = resultSet.getBoolean("experience_rating");
                String recommendations = resultSet.getString("recommendations");
                String feedbackComments = resultSet.getString("feedback_comments");

                Feedback feedback = new Feedback(id, patientName, doctorName, hospitalName, experienceRating, recommendations, feedbackComments);
                System.out.println(feedbackComments);
                feedbackList.add(feedback);
                System.out.println(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return feedbackList;
    }


}
