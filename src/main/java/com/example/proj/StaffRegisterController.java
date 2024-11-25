package com.example.proj;

import com.example.temp.DB_HANDLER.Hospital_Handler;
import com.example.temp.DB_HANDLER.Staff_Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;

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
    private DatePicker dobField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> hoscomb;
    public void initialize()
    {
        populateHospitals(); // Populate hospitals on initialization
    }
    private void populateHospitals() {
        Hospital_Handler hospitalHandler = new Hospital_Handler(); // Assuming this is your handler class
        List<Hospital> hospitalList = hospitalHandler.getAllHospitals(); // Fetch all hospitals

        if (hospitalList != null && !hospitalList.isEmpty()) {
            ObservableList<String> hospitals = FXCollections.observableArrayList();

            // Add hospital names to the ObservableList
            for (Hospital hospital : hospitalList) {
                hospitals.add(hospital.getName()); // Assuming `Hospital` has a `getName()` method
            }

            hoscomb.setItems(hospitals);
        } else {
            showAlert("Info", "No hospitals found in the database.", Alert.AlertType.INFORMATION);
        }
    }

    // Utility method to show alerts

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
        LocalDate dob = dobField.getValue();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String selectedHospital = hoscomb.getValue();

        // Validate inputs
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            showAlert("Error", "First name should only contain alphabetic characters.", Alert.AlertType.ERROR);
            return;
        }
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            showAlert("Error", "Last name should only contain alphabetic characters.", Alert.AlertType.ERROR);
            return;
        }



        // Validate contact number (must be 10 digits)
        if (contactNo.isEmpty() || !contactNo.matches("[0-9]{11}")) {
            showAlert("Error", "Contact number must be a 11-digit number.", Alert.AlertType.ERROR);
            return;
        }

        // Validate date of birth (basic check for format, could be enhanced to validate age)
        if (dob == null) {
            showAlert("Error", "Date of Birth is required!", Alert.AlertType.ERROR);
            return;
        }

        if (selectedHospital == null || selectedHospital.isEmpty()) {
            showAlert("Error", "Please select a hospital from the dropdown.", Alert.AlertType.ERROR);
            return;
        }

        LocalDate currentDate = LocalDate.now();
        if (dob.isAfter(currentDate)) {
            showAlert("Error", "Date of Birth cannot be in the future!",Alert.AlertType.ERROR);
            return;
        }
        // Validate address (not empty)
        if (address.isEmpty()) {
            showAlert("Error", "Address cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Validate username (only alphanumeric characters and not empty)
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9_]+")) {
            showAlert("Error", "Username must be alphanumeric and cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Validate password (at least 6 characters)
        if (password.isEmpty() || password.length() < 8) {
            showAlert("Error", "Password must be at least 8 characters long.", Alert.AlertType.ERROR);
            return;
        }

        // Register staff in the database
        Staff_Handler handler = new Staff_Handler();
        // Convert LocalDate to String in the "yyyy-MM-dd" format
        String dobString = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean isRegistered = Staff.registerStaff(firstName, lastName, contactNo, dobString, address, username, password,selectedHospital);


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
