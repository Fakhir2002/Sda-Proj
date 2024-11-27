package Database;

import OOP.MedicalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistory_Handler implements DatabaseConfig {

    // Load medical history specific to a patient
    public List<MedicalRecord> getMedicalHistory(int patientId) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        String query = "SELECT * FROM videoconsulations WHERE patientID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalRecords.add(new MedicalRecord(
                        resultSet.getInt("historyID"),
                        resultSet.getString("symptoms"),
                        resultSet.getString("Diagnosis"),
                        resultSet.getString("Treatment"),
                        resultSet.getString("Date"),
                        resultSet.getBoolean("isUpdated")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalRecords;
    }

    public List<MedicalRecord> getReport(int patientId) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        // Updated query to check for 'isUpdated' being true
        String query = "SELECT * FROM videoconsulations WHERE patientID = ? AND isUpdated = true";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalRecords.add(new MedicalRecord(
                        resultSet.getInt("historyID"),
                        resultSet.getString("symptoms"),
                        resultSet.getString("Diagnosis"),
                        resultSet.getString("Treatment"),
                        resultSet.getString("Date"),
                        resultSet.getBoolean("isUpdated")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalRecords;
    }


    // Save new medical history record (initially without diagnosis and treatment)
    public boolean saveMedicalHistory(String symptoms, int patientId, int doctorId) {
        String query = "INSERT INTO videoconsulations (symptoms, patientID, doctorID, isUpdated) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, symptoms);
            preparedStatement.setInt(2, patientId);
            preparedStatement.setInt(3, doctorId);
            preparedStatement.setBoolean(4, false); // Initially, isUpdated is false

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update diagnosis and treatment in an existing medical history record
    public boolean updateMedicalHistory(int historyID, String diagnosis, String treatment, String date) {
        String query = "UPDATE videoconsulations SET Diagnosis = ?, Treatment = ?, Date = ?, isUpdated = ? WHERE historyID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, diagnosis);
            preparedStatement.setString(2, treatment);
            preparedStatement.setString(3, date);
            preparedStatement.setBoolean(4, true); // Mark as updated
            preparedStatement.setInt(5, historyID);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the patient IDs for consultations with a specific doctor that are not updated.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of patient IDs who have video consultations that are not updated.
     */
    public List<Integer> getConsultationsForDoctor(int doctorId) {
        List<Integer> patientIds = new ArrayList<>();
        String query = "SELECT patientID FROM videoconsulations WHERE doctorID = ? AND isUpdated = false";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, doctorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Iterate through the result set and add patient IDs to the list
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("patientID");
                    patientIds.add(patientId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientIds;
    }

    public boolean hasExistingVideoConsultation(int patientId, int doctorId) {
        String query = "SELECT * FROM videoconsulations WHERE patientID = ? AND doctorID = ? AND isUpdated = false";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a record exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
