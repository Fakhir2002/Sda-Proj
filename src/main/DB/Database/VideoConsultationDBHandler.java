package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoConsultationDBHandler implements DatabaseConfig{

    // Method to insert a new video consultation into the database
    public static boolean saveVideoConsultation(String status, int patientId, int doctorId) {
        String query = "INSERT INTO VideoConsultation (status, patient_id, doctor_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
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

    // Method to fetch all consultations for a given doctor
    public static List<Integer> getConsultationsForDoctor(int doctorId) {
        List<Integer> patientIds = new ArrayList<>();
        String query = "SELECT patient_id FROM VideoConsultation WHERE doctor_id = ?";

        try (Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the doctor_id for the prepared statement
            preparedStatement.setInt(1, doctorId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Extract patient IDs from the result set
            while (resultSet.next()) {
                patientIds.add(resultSet.getInt("patient_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception (e.g., log or rethrow)
        }
        return patientIds;
    }
}
