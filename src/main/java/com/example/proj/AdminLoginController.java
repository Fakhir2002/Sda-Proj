package com.example.proj;

import com.example.temp.DB_HANDLER.AdminLogin_Handler;
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

public class AdminLoginController {

    @FXML
    private TextField usernameField; // TextField for username input
    @FXML
    private TextField passwordField; // TextField for password input
    @FXML
    private Button AdminLogin; // Login button
    @FXML
    private Button hello; // Back button

    private final AdminLogin_Handler dbHandler = new AdminLogin_Handler();

    /**
     * Handles the "Back" button action, navigates to the Home Page.
     */
    public void adminback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) hello.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    /**
     * Handles the "Login" button action, validates admin credentials.
     */
    public void handleAdminLogin(ActionEvent actionEvent) {
        // Get the input values from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate the input credentials
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
            return;
        }

        // Check credentials against the database
        boolean isValid = dbHandler.validateLogin(username, password);
        if (isValid) {
            try {
                // Load Admin Home Page if login is successful
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) AdminLogin.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Admin Home Page");
                currentStage.sizeToScene();
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Debugging in case of issues loading the FXML
            }
        } else {
            // Show an error alert for invalid credentials
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    /**
     * Utility method to show an alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
