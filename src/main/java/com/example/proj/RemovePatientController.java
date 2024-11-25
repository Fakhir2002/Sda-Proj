package com.example.proj;

import Database.Admin_Handler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;

public class RemovePatientController {

    @FXML
    private Button patientback;

    @FXML
    private ComboBox<String> removepatcombobox; // ComboBox for patient names
    private ObservableList<String> patientList; // List for patient names
    private Admin_Handler adminHandler = new Admin_Handler();

    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    @FXML
    public void initialize() {
        populatePatientComboBox();
    }

    public void populatePatientComboBox() {
        removepatcombobox.getItems().clear(); // Clear ComboBox to avoid duplicates

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT first_name FROM patients")) {

            while (resultSet.next()) {
                String patientName = resultSet.getString("first_name"); // Get patient first name
                removepatcombobox.getItems().add(patientName); // Add patient name to ComboBox
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemovePatient(ActionEvent event) {
        // Get the selected patient's first name
        String selectedPatientName = removepatcombobox.getSelectionModel().getSelectedItem();

        if (selectedPatientName != null && !selectedPatientName.isEmpty()) { // Ensure a patient is selected
            // Call Admin_Handler to remove the patient by their first name
            boolean isRemoved = Admin.removePatient(selectedPatientName);

            if (isRemoved) {
                showAlert("Success", "Patient removed successfully!", Alert.AlertType.INFORMATION);

                // Remove patient from ComboBox
                removepatcombobox.getItems().remove(selectedPatientName);

            } else {
                showAlert("Error", "Failed to remove the patient.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select a patient to remove.", Alert.AlertType.WARNING);
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handlepatientback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) patientback.getScene().getWindow();

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin's Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
