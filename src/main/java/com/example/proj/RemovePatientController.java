package com.example.proj;

import Database.Admin_Handler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RemovePatientController {

    @FXML
    private Button patientback;

    @FXML
    private ComboBox<String> removepatcombobox; // ComboBox for patient names

    private final Admin_Handler adminHandler = new Admin_Handler();

    @FXML
    public void initialize() {
        populatePatientComboBox();
    }

    public void populatePatientComboBox() {
        removepatcombobox.getItems().clear(); // Clear ComboBox to avoid duplicates
        List<String> patientNames = adminHandler.getPatientNames(); // Fetch patient names from the handler
        removepatcombobox.getItems().addAll(patientNames); // Populate ComboBox with patient names
    }

    @FXML
    private void handleRemovePatient(ActionEvent event) {
        String selectedPatientName = removepatcombobox.getSelectionModel().getSelectedItem(); // Get selected name

        if (selectedPatientName != null && !selectedPatientName.isEmpty()) { // Ensure a patient is selected
            boolean isRemoved = adminHandler.removePatient(selectedPatientName); // Delegate removal to handler

            if (isRemoved) {
                showAlert("Success", "Patient removed successfully!", Alert.AlertType.INFORMATION);
                removepatcombobox.getItems().remove(selectedPatientName); // Remove from ComboBox
            } else {
                showAlert("Error", "Failed to remove the patient.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select a patient to remove.", Alert.AlertType.WARNING);
        }
    }

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
