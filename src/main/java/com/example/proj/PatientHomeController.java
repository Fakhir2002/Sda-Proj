package com.example.proj;

import com.example.temp.DB_HANDLER.Patient_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientHomeController {

    @FXML
    private Button faqsButton;

    @FXML
    private Button BookAppointment;

    @FXML
    private Button Compare;

    @FXML
    private Button Feedback;

    @FXML
    private Button emergency;

    @FXML
    private Button payment;

    @FXML
    private Button patlogout;

    @FXML
    private Label PatientName;

    private Patient_Handler patientHandler = new Patient_Handler(); // Handler to interact with the database
    private Patient currentPatient; // Stores the logged-in patient's details

    /**
     * Setter to initialize the patient data based on the username.
     * Fetches the patient's details from the database and sets up the home page.
     *
     * @param username The username of the logged-in patient.
     */
    public void setPatientName(String username) {
        // Retrieve patient details using the handler
        currentPatient = patientHandler.getPatientDetails(username);

        if (currentPatient != null) {
            // Display the patient's name on the home page
            PatientName.setText("Welcome, " + currentPatient.getFirstName() + " " + currentPatient.getLastName() + "!");
        } else {
            PatientName.setText("Welcome, Guest!"); // Fallback in case of an error
        }
    }

    public void HandleBookAppointment(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookAppointment.fxml"));
            Parent newPage = loader.load();

            // Pass the patient object to the next controller if needed
            // Example: ((BookAppointmentController) loader.getController()).setPatient(currentPatient);

            Stage currentStage = (Stage) BookAppointment.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Book Appointment");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandleCompare(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Comparison.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) Compare.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Comparison");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandleFeedback(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Submit-Feedback.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) Feedback.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Feedback");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandleEmergency(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Emergency.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) emergency.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Emergency");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandleFaqs(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientFaq.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) faqsButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("FAQs");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandlePayments(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pay-Bills.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) payment.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Bills");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlepatlogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) patlogout.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
