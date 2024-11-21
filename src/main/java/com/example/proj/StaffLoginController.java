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

import com.example.temp.DB_HANDLER.StaffLogin_Handler; // Correct import for the StaffLogin_Handler

import java.io.IOException;

public class StaffLoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button stafflogin;
    @FXML
    private Button hello3;

    private StaffLogin_Handler staffLoginHandler;

    public StaffLoginController() {
        staffLoginHandler = new StaffLogin_Handler();
    }

    public void staffgoback(ActionEvent actionEvent) {
        try {
            // Load the FXML for the Home Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) hello3.getScene().getWindow();

            // Create a new stage
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handlestafflogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        // Call the login handler to verify the credentials
        boolean loginSuccess = staffLoginHandler.loginStaff(username, password);

        if (loginSuccess) {
            try {
                // Load the FXML for the Staff Home Page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHome.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) stafflogin.getScene().getWindow();

                // Create a new stage
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Staff Home Page");
                currentStage.sizeToScene();
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Debugging in case of issues loading the FXML
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
