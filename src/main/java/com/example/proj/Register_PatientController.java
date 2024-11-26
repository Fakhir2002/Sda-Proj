package com.example.proj;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Register_PatientController {
    public Button PatRegCancel;
    public Button patientalready;
    @FXML
    private Button patreg;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField contactNoField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public void handlePatientRegCancel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) PatRegCancel.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PatientAlreadyAccount(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientLogin.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) PatRegCancel.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Patient Login");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlepatreg(ActionEvent actionEvent) {
        // Gather input from fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactNo = contactNoField.getText();
        LocalDate dob = dobField.getValue();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "First Name must contain only letters and cannot be empty.");
            return;
        }
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Last Name must contain only letters and cannot be empty.");
            return;
        }
        if (contactNo.isEmpty() || !contactNo.matches("[0-9]{11}")) { // assuming an 11-digit number
            showAlert(Alert.AlertType.ERROR, "Error", "Contact Number must be an 11-digit number.");
            return;
        }
        if (dob == null || dob.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid Date of Birth.");
            return;
        }
        if (address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Address cannot be empty.");
            return;
        }
        if (username.isEmpty()) { // Check if the username is empty
            showAlert(Alert.AlertType.ERROR, "Error", "Username cannot be empty.");
            return;
        }
        if (password.isEmpty() || password.length() < 8) { // Assuming password must be at least 8 characters
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters long.");
            return;
        }

        String Sdob = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // Attempt to insert data into the database
        boolean success = Patient.registerPatient(firstName, lastName, contactNo, Sdob, address, username, password);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Patient registered successfully.");
            try {
                // Navigate to the PatientLogin page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientLogin.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) patreg.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Patient Login");
                currentStage.sizeToScene();
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register patient. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
