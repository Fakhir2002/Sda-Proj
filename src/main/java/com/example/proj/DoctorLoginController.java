package com.example.proj;

import com.example.temp.DB_HANDLER.DoctorLogin_Handler;
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
    private Button hello1;

    @FXML
    private Button DocLog;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private DoctorLogin_Handler doctorLoginHandler = new DoctorLogin_Handler();  // Instantiate the login handler

    /**
     * Handles the "Go back" action to the home page.
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
            e.printStackTrace();
        }
    }

    /**
     * Handles the doctor login process.
     * Verifies the credentials and navigates to the doctor's home page if successful.
     */
    public void handleDocLogin(ActionEvent actionEvent) {
        // Gather input from fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate if both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both username and password.");
            return;
        }

        // Attempt to validate login credentials
        boolean loginSuccess = doctorLoginHandler.validateLogin(username, password);

        if (loginSuccess) {
            // If login is successful, navigate to the Doctor Home page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorHome.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) DocLog.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Doctor Home Page");
                currentStage.sizeToScene();
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If login fails, show an error alert
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
        }
    }

    /**
     * Utility method to show alert dialogs.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
