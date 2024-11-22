package com.example.proj;

import com.example.temp.DB_HANDLER.AdminRegister_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminRegisterController {

    public Button adminRegCancel;
    public Button adminalready;
    @FXML
    private Button adminreg;

    @FXML
    private TextField firstNameField;  // Admin's first name input
    @FXML
    private TextField lastNameField;   // Admin's last name input
    @FXML
    private TextField contactNoField;  // Admin's contact number input
    @FXML
    private DatePicker dobField;        // Admin's DOB input
    @FXML
    private TextField addressField;    // Admin's address input
    @FXML
    private TextField usernameField;   // Admin's username input
    @FXML
    private TextField passwordField;   // Admin's password input

    private final AdminRegister_Handler adminHandler = new AdminRegister_Handler();

    public void handleAdminRegCancel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) adminRegCancel.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AdminAlreadyAccount(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) adminalready.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin Login");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleadminreg(ActionEvent actionEvent) {
        // Get input data from fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactNo = contactNoField.getText();
        LocalDate dob = dobField.getValue();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate inputs (basic validation)
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "First name should only contain alphabetic characters.");
            return;
        }
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Last name should only contain alphabetic characters.");
            return;
        }

        // Validate contact number (must be 10 digits)
        if (contactNo.isEmpty() || !contactNo.matches("[0-9]{11}")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Contact number must be a 11-digit number.");
            return;
        }
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "First name should only contain alphabetic characters.");
            return;
        }
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Last name should only contain alphabetic characters.");
            return;
        }

        // Validate contact number (must be 10 digits)
        if (contactNo.isEmpty() || !contactNo.matches("[0-9]{11}")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Contact number must be a 11-digit number.");
            return;
        }

        // Validate date of birth (must be in the past and a valid date)
        if (dob == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Date of birth is required.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        if (dob.isAfter(currentDate)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Date of Birth cannot be in the future!");
            return;
        }


        // Validate address (not empty)
        if (address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Address cannot be empty.");
            return;
        }

        // Validate username (only alphanumeric characters and not empty)
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9_]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username must be alphanumeric and cannot be empty.");
            return;
        }

        // Validate password (at least 6 characters)
        if (password.isEmpty() || password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters long.");
            return;
        }
        // Validate date of birth (must be in the past and a valid date)

        // Validate address (not empty)
        if (address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Address cannot be empty.");
            return;
        }

        // Validate username (only alphanumeric characters and not empty)
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9_]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username must be alphanumeric and cannot be empty.");
            return;
        }

        // Validate password (at least 6 characters)
        if (password.isEmpty() || password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 6 characters long.");
            return;
        }

        // Call the database handler to register the admin
        String dobstring = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean isRegistered = adminHandler.registerAdmin(firstName, lastName, contactNo, dobstring, address, username, password);

        if (isRegistered) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Admin registered successfully!");
            navigateToAdminLogin(); // Navigate to the Admin Login page on success
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register admin. Please try again.");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Navigate to the Admin Login page
    private void navigateToAdminLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) adminreg.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Admin Login Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
