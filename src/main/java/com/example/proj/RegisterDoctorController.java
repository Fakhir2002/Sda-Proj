package com.example.proj;

import com.example.temp.DB_HANDLER.DoctorRegister_Handler;
import com.example.temp.DB_HANDLER.addHospital_Handler;
import javafx.collections.FXCollections;
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

public class RegisterDoctorController {

    @FXML
    private Button docRegCancel;
    @FXML
    private Button doctoralready;
    @FXML
    private Button docreg;

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ComboBox<String> hospitalField; // Updated to ComboBox<String>
    @FXML
    private TextField specialtyField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private final DoctorRegister_Handler doctorRegisterHandler = new DoctorRegister_Handler();
    private final addHospital_Handler hospitalHandler = new addHospital_Handler();
    /**
     * Initializes the controller.
     * Populates the hospital ComboBox with hospital names.
     */
    @FXML
    public void initialize() {
        populateHospitalComboBox();
    }

    /**
     * Populates the hospital ComboBox with hospital names from the database.
     */
    private void populateHospitalComboBox() {
        try {
            // Fetch the list of hospital names from the database
            List<String> hospitalNames = hospitalHandler.getHospitalNames();
            // Populate the ComboBox with hospital names
            hospitalField.setItems(FXCollections.observableArrayList(hospitalNames));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load hospital names.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the cancel action and navigates back to the home page.
     */
    public void HandelDocCancel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) docRegCancel.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the doctor login page for users with an existing account.
     */
    public void DoctorAlreadyAccount(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) doctoralready.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Doctor Login");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the doctor registration process and navigates to the login page on success.
     */
    public void handledocreg(ActionEvent actionEvent) {
        // Gather input from fields
        String name = nameField.getText();
        LocalDate dob = dobPicker.getValue();
        String hospital = hospitalField.getValue(); // Retrieve selected hospital name
        String specialty = specialtyField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (name.isEmpty() || dob == null || hospital == null || specialty.isEmpty() ||
                contact.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        // Validate specialty (ensure it's not empty)
        if (!specialty.matches("[a-zA-Z ]+")) { // Allows only letters and spaces
            showAlert(Alert.AlertType.ERROR, "Error", "Specialty cannot contain numbers.");
            return;
        }
        // Validate contact number (assuming it should be a 10-digit number)
        if (!contact.matches("[0-9]{11}")) { // Assuming 10-digit contact number
            showAlert(Alert.AlertType.ERROR, "Error", "Contact number must be 11 digits.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        if (dob.isAfter(currentDate)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Date of Birth cannot be in the future!");
            return;
        }


        // Validate username (if you want it to be alphanumeric)
        if (username.isEmpty() || !username.matches("[a-zA-Z0-9_]+")) { // Assuming alphanumeric username
            showAlert(Alert.AlertType.ERROR, "Error", "Username must be alphanumeric and cannot be empty.");
            return;
        }

        // Validate password (minimum length check, you can modify as needed)
        if (password.isEmpty() || password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 6 characters long.");
            return;
        }

        // Attempt to insert data into the database
        String dobString = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean success = doctorRegisterHandler.registerDoctor(name, dobString, hospital, specialty, contact, address, username, password);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Doctor registered successfully.");
            try {
                // Navigate to the DoctorLogin page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
                Parent newPage = loader.load();

                Stage currentStage = (Stage) docreg.getScene().getWindow();
                currentStage.setScene(new Scene(newPage));
                currentStage.setTitle("Doctor Login");
                currentStage.sizeToScene();
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register doctor. Please try again.");
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
