package com.example.proj;

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

public class ComparisonController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button PatientHome;

    @FXML
    private Button proceedButton;

    @FXML
    private ComboBox<String> comparisonBox; // ComboBox for selecting comparison type

    /**
     * Initialize the ComboBox and set up default options.
     */
    public void initialize() {
        // Populate the ComboBox with options
        comparisonBox.getItems().addAll("Doctor Comparison", "Hospital Comparison");

        // Set a default value
        comparisonBox.setValue("Doctor Comparison");
    }

    /**
     * Handles the "Back" button action to navigate to the PatientHome screen.
     */
    @FXML
    public void HandleBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) PatientHome.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Proceed" button action to navigate based on the ComboBox selection.
     */
    @FXML
    public void handleProceed(ActionEvent actionEvent) {
        String selectedOption = comparisonBox.getValue();

        if (selectedOption == null) {
            System.out.println("No option selected."); // Optional: Add validation message for the user
            return;
        }

        if ("Doctor Comparison".equals(selectedOption)) {
            navigateToComparison("DoctorComparison.fxml", "Doctor Comparison");
        } else if ("Hospital Comparison".equals(selectedOption)) {
            navigateToComparison("HospitalComparison.fxml", "Hospital Comparison");
        }
    }

    /**
     * Navigate to the appropriate comparison screen based on user selection.
     *
     * @param fxmlFile The FXML file to load.
     * @param title    The title of the new stage.
     */
    private void navigateToComparison(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) proceedButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
