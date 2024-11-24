package com.example.proj;


import com.example.temp.DB_HANDLER.Emergency_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EmergencyController implements InitializeUsername {

    @FXML
    private Button Submit;
    @FXML 
    private TextField descriptionBox;
    @FXML
    private Label welcomeText;

    @FXML
    private Button BackButton;

    @FXML
    private ComboBox<String> hospitalComboBox;  // ComboBox to show hospital names

    @FXML
    private ComboBox<String> type;  // ComboBox to show emergency types

    private Patient currentPatient;
    private Emergency emergency;  // Instance of Emergency class
    private Hospital hospital;


    public void initialize(String username) {
        // Initialize Patient object
        currentPatient = new Patient(username);
        hospital = new Hospital();

        // Initialize Emergency object with the handler
        emergency = new Emergency();

        // Populate the combo box with hospital names
        populateHospitalComboBox();

        // Populate the type ComboBox with dummy emergency types
        populateEmergencyTypeComboBox();  // Ensure this method is called here
    }

    public void Handleback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) BackButton.getScene().getWindow();
            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Method to retrieve hospital names from the database using Emergency_Handler
    private void populateHospitalComboBox() {
        List<String> hospitalNames = emergency.fetchHospitalNames();  // Use the fetchHospitalNames() method

        // Debugging: Check if the hospital names are retrieved
        System.out.println("Hospital names fetched: " + hospitalNames);

        // Add hospital names to the ComboBox
        hospitalComboBox.getItems().addAll(hospitalNames);
    }

    // Method to populate emergency types in the 'type' ComboBox
    private void populateEmergencyTypeComboBox() {
        // List of dummy emergency types
        String[] emergencyTypes = {"Accident", "Heart Attack", "Stroke", "Severe Bleeding", "Broken Bone", "Poisoning"};

        // Add emergency types to the ComboBox
        type.getItems().addAll(emergencyTypes);

        // Debugging: Verify if the combo box is populated
        System.out.println("Emergency types populated: " + emergencyTypes);
    }

    public void handleEmergency(ActionEvent actionEvent) {
        // Get data from UI elements
        String description = descriptionBox.getText();  // Get the description from the text field
        String emergencyType = type.getSelectionModel().getSelectedItem();  // Get selected emergency type
        String selectedHospital = hospitalComboBox.getSelectionModel().getSelectedItem();  // Get selected hospital

        // Ensure all necessary data is entered
        if (description.isEmpty() || emergencyType == null || selectedHospital == null) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Form", "Please fill in all fields!");
            return; // Early exit if data is incomplete
        }

        // Get patient id from currentPatient object
        int patientId = currentPatient.getId();

        // Get hospital id based on selected hospital name
        int hospitalId = hospital.getHospitalIdByName(selectedHospital); // Assuming this method exists in Hospital class

        // Default status for new emergency record
        String status = "Pending";

        // Insert emergency record
        boolean isInserted = emergency.insertEmergency(patientId, hospitalId, emergencyType, status, description);

        if (isInserted) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Emergency record inserted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error inserting emergency record.");
        }
    }

    /**
     * Show an alert with a specific type, title, and message.
     *
     * @param alertType The type of alert (e.g., WARNING, ERROR, INFORMATION)
     * @param title     The title of the alert
     * @param message   The message of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);  // No header
        alert.setContentText(message);

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

}
