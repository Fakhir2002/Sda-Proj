package com.example.proj;

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

public class Patient_LoginController {

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
        loadPage("HomePage.fxml", "Home Page", hello2);
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

        if (Patient.validateLogin(username, password)) {
            loadPatientHomePage(username);
        } else {
            // Show an error alert if credentials are invalid
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    // Utility method to load pages
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) currentButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Navigate to the Patient Home Page and pass the username
    private void loadPatientHomePage(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            // Pass the username to the PatientHomeController
            PatientHomeController controller = loader.getController();
            controller.initialize(username);

            Stage currentStage = (Stage) patientlog.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Patient Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
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
