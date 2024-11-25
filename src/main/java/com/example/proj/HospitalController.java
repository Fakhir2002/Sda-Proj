package com.example.proj;

import Database.Hospital_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HospitalController {

    @FXML
    private Button backButton; // Back button for returning to the previous page
    @FXML
    private Button Add; // Add button for adding a hospital
    @FXML
    private TextField Name; // TextField for entering the hospital name

    // Hospital handler for managing hospital database operations
    private final Hospital_Handler hospitalHandler;

    // Constructor to initialize the handler
    public HospitalController() {
        hospitalHandler = new Hospital_Handler(); // Initialize the handler
    }

    /**
     * Handles the action for the "Back" button.
     * Navigates the user back to the Admin's Home Page.
     */
    public void handleBack(ActionEvent actionEvent) {
        try {
            // Load the Admin's Home Page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent newPage = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin's Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception for debugging
            showAlert("Error", "Unable to navigate back. Please try again.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the action for the "Add" button.
     * Adds a hospital to the database using the Hospital_Handler.
     */
    public void handleAdd(ActionEvent actionEvent) {
        String hospitalName = Name.getText().trim(); // Get and trim the input

        // Validate the input
        if (hospitalName.isEmpty()) {
            showAlert("Validation Error", "Hospital name cannot be empty.", Alert.AlertType.WARNING);
            return;
        }

        // Attempt to add the hospital
        boolean success = hospitalHandler.addHospital(hospitalName);

        if (success) {
            showAlert("Success", "Hospital added successfully!", Alert.AlertType.INFORMATION);
            Name.clear(); // Clear the input field for new entries
        } else {
            showAlert("Error", "Failed to add hospital. Please try again.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Utility method to display alerts.
     *
     * @param title     The title of the alert.
     * @param content   The content/message of the alert.
     * @param alertType The type of the alert (e.g., ERROR, WARNING, INFORMATION).
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
