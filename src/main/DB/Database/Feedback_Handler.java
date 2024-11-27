package Database;
import OOP.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback_Handler implements DatabaseConfig{


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
    public boolean insertFeedback( String patientName, String doctorName, String hospitalName, String experienceRating,
                                  String recommendations, String feedbackComments) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FEEDBACK_QUERY)) {

            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, doctorName);
            preparedStatement.setString(3, hospitalName);
            preparedStatement.setString(4, experienceRating);  // true for "Yes", false for "No"
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
    public List<Feedback> getAllFeedback(String doctorName) {
        List<Feedback> feedbackList = new ArrayList<>();

        // SQL query to fetch feedback for a specific doctor
        String query = "SELECT * FROM feedback WHERE doctor_name = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the doctorName parameter in the query
            preparedStatement.setString(1, doctorName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Iterate through the result set and create Feedback objects
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String patientName = resultSet.getString("patient_name");
                    String doctorNameFromDB = resultSet.getString("doctor_name"); // To verify retrieved data
                    String hospitalName = resultSet.getString("hospital_name");
                    String experienceRating = resultSet.getString("experience_rating");
                    String recommendations = resultSet.getString("recommendations");
                    String feedbackComments = resultSet.getString("feedback_comments");

                    // Create and add Feedback objects to the list
                    Feedback feedback = new Feedback(id, patientName, doctorNameFromDB, hospitalName, experienceRating, recommendations, feedbackComments);
                    feedbackList.add(feedback);

                    // Debugging logs to ensure correct data retrieval
                    System.out.println("Feedback ID: " + id + ", Comments: " + feedbackComments);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList; // Return filtered feedback
    }



}
