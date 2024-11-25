package com.example.proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MedicalHistoryController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/user";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    @FXML
    private TableView<MedicalRecord> medicalHistoryTable;

    @FXML
    private TableColumn<MedicalRecord, String> diagnosisColumn;

    @FXML
    private TableColumn<MedicalRecord, String> treatmentColumn;

    @FXML
    private TableColumn<MedicalRecord, String> dateDiagnosedColumn;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextArea treatmentTextArea;

    @FXML
    private Button updateButton;

    @FXML
    private Button nikal;

    private ObservableList<MedicalRecord> medicalRecords;

    private Patient currentPatient; // Updated to store patient details
    private Doctor currentDoctor;
    // Initialize method
    public void initialize(String patientUsername, String doctorUsername) {
        currentPatient = new Patient(patientUsername);
        currentDoctor = new Doctor(doctorUsername);

        // Bind TableColumn fields to MedicalRecord properties
        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        treatmentColumn.setCellValueFactory(cellData -> cellData.getValue().treatmentProperty());
        dateDiagnosedColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Load medical history for the specific patient
        loadMedicalHistory();
    }

    // Load medical history specific to the patient
    private void loadMedicalHistory() {
        medicalRecords = FXCollections.observableArrayList();

        String query = "SELECT * FROM medicalhistory WHERE patientID = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, currentPatient.getId()); // Use patient ID
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalRecords.add(new MedicalRecord(
                        resultSet.getString("Diagnosis"),
                        resultSet.getString("Treatment"),
                        resultSet.getString("Date")
                ));
            }

            medicalHistoryTable.setItems(medicalRecords);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save new medical history record
    @FXML
    private void handleSave(ActionEvent event) {
        String diagnosis = diagnosisTextArea.getText();
        String treatment = treatmentTextArea.getText();
        String currentDate = java.time.LocalDate.now().toString(); // Get current date

        if (diagnosis.isEmpty() || treatment.isEmpty()) {
            showAlert("Error", "Fields cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        String query = "INSERT INTO medicalhistory (Diagnosis, Treatment, Date, patientID) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, diagnosis);
            preparedStatement.setString(2, treatment);
            preparedStatement.setString(3, currentDate);
            preparedStatement.setInt(4, currentPatient.getId()); // Add patient ID

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Medical record saved successfully!", Alert.AlertType.INFORMATION);
                loadMedicalHistory(); // Refresh table view
                diagnosisTextArea.clear();
                treatmentTextArea.clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Back button handler
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
            Parent root = loader.load();

            DoctorHomeController controller = loader.getController();
            controller.initialize(currentDoctor.getUsername());

            Stage currentStage = (Stage) nikal.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method to display alerts
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
