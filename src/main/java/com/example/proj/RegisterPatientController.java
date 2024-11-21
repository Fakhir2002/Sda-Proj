package com.example.proj;

import com.example.temp.DB_HANDLER.PatientRegister_Handler;
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

public class RegisterPatientController {
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

    private PatientRegister_Handler patientRegisterHandler = new PatientRegister_Handler();

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
        if (firstName.isEmpty() || lastName.isEmpty() || contactNo.isEmpty() || dob==null ||
                address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        String Sdob = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // Attempt to insert data into the database
        boolean success = patientRegisterHandler.registerPatient(firstName, lastName, contactNo, Sdob, address, username, password);

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
