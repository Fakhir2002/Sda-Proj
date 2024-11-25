package com.example.proj;

import Database.Doctor_Handler;
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

public class DoctorLoginController {
    @FXML
    private Button hello1; // Back button

    @FXML
    private Button DocLog; // Login button

    @FXML
    private TextField usernameField; // Field to enter username

    @FXML
    private PasswordField passwordField; // Field to enter password

    // Login handler for validating credentials
    private final Doctor_Handler loginHandler = new Doctor_Handler();



    /**
     * Handles the "Back" button action.
     */
    public void doctorgoback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) hello1.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    /**
     * Handles the login process by validating credentials.
     */
    public void handleDocLogin(ActionEvent actionEvent) {
        // Get input from text fields
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        // Verify credentials with the database
        boolean isValid = Doctor.validateLogin(username, password);

        if (isValid) {
            // If credentials are valid, navigate to DoctorHome.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
                Parent newPage = loader.load();

                DoctorHomeController controller = loader.getController();

                // Pass the username to the PatientHomeController
                controller.initialize(username);

                Stage currentStage = (Stage) DocLog.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Doctor Home Page");
                currentStage.sizeToScene();
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Debugging in case of issues loading the FXML
            }
        } else {
            // Show error alert for invalid credentials
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
        }
    }



    /**
     * Utility method to show alerts.
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
