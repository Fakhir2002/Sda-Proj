package com.example.proj;

import com.example.temp.DB_HANDLER.Emergency_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EmergencyController implements InitializeUsername {

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

    private Emergency_Handler emergencyHandler;  // Declare emergencyHandler here

    public EmergencyController() {
        this.emergencyHandler = new Emergency_Handler();  // Initialize emergencyHandler here
    }

    public void initialize(String username) {
        // Initialize Patient object
        currentPatient = new Patient(username);

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
}
