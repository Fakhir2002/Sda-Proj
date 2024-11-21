package com.example.proj;

import com.example.temp.DB_HANDLER.StaffRegister_Handler;
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

public class StaffRegisterController {
    @FXML
    private Button staffRegCancel;
    @FXML
    private Button staffalready;
    @FXML
    private Button StaffRegButton;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField contactNoField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    // Handle cancel button
    public void handleStaffRegCancel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) staffRegCancel.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Handle "Already have an account" button
    public void StaffAlreadyAccount(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffLogin.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) staffalready.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Staff Login");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Handle registration
    public void handleStaffReg(ActionEvent actionEvent) {
        // Retrieve input values
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactNo = contactNoField.getText();
        String dob = dobField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || contactNo.isEmpty() ||
                dob.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        // Register staff in the database
        StaffRegister_Handler handler = new StaffRegister_Handler();
        boolean isRegistered = handler.registerStaff(firstName, lastName, contactNo, dob, address, username, password);

        if (isRegistered) {
            showAlert("Success", "Staff registered successfully!", Alert.AlertType.INFORMATION);

            // Navigate to the login page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffLogin.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) StaffRegButton.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Staff Login Page");
                currentStage.sizeToScene();
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Failed to register staff. Please try again.", Alert.AlertType.ERROR);
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
