package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EmergencyController implements InitializeUsername{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private Button BackButton;

    @FXML
    private Patient currentPatient;

    public void initialize(String username) {
        // Initialize Patient and Appointment objects
        currentPatient = new Patient(username);
    }

    public void Handleback(ActionEvent actionEvent) {



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHome.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) BackButton.getScene().getWindow();
            PatientHomeController controller = loader.getController();
            controller.initialize(currentPatient.getUsername());


            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("PatientHome");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }

    }


}
