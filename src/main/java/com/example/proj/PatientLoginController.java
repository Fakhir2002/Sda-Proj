package com.example.proj;


import com.example.temp.DB_HANDLER.Patient_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientLoginController {
    @FXML
    private Button hello2; // Back button

    @FXML
    private Button patientlog; // Login button

    @FXML
    private TextField usernameField; // Username input field

    @FXML
    private PasswordField passwordField; // Password input field

    // Handles the "Back" button action
    public void patientgoback(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) hello2.getScene().getWindow();

            // Update the current stage with the new scene
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Handles the "Login" button action
    public void HandlePatientLog(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Username or password cannot be empty.");
            return;
        }


        Patient_Handler loginHandler = new Patient_Handler();
        if (loginHandler.validateLogin(username, password)) {
            try {
                // Load the FXML for the Patient Home Page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
                Parent newPage = loader.load();


                // Get the controller for the PatientHome screen
                PatientHomeController controller = loader.getController();

                // Pass the username to the PatientHomeController
                controller.setPatientName(username); // Set the username on the home page label


                Stage currentStage = (Stage) patientlog.getScene().getWindow();

                // Update the current stage with the new scene
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Patient Home Page");
                currentStage.sizeToScene();
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Log the error for debugging
            }
        } else {
            // Show an error alert if credentials are invalid
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    // Utility method to display an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}