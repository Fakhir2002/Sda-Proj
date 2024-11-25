package com.example.temp.DB_HANDLER;

import com.example.proj.MedicalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistory_Handler implements DatabaseConfig {

    // Load medical history specific to a patient
    public List<MedicalRecord> getMedicalHistory(int patientId) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        String query = "SELECT * FROM medicalhistory WHERE patientID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalRecords.add(new MedicalRecord(
                        resultSet.getString("Diagnosis"),
                        resultSet.getString("Treatment"),
                        resultSet.getString("Date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalRecords;
    }

    // Save new medical history record
    public boolean saveMedicalHistory(String diagnosis, String treatment, String date, int patientId) {
        String query = "INSERT INTO medicalhistory (Diagnosis, Treatment, Date, patientID) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, diagnosis);
            preparedStatement.setString(2, treatment);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, patientId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
