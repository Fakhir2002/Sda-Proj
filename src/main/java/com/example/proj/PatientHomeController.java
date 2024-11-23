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

public class PatientHomeController implements InitializeUsername{

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

    @FXML
    private Patient currentPatient;
    private String username;

    // Setter to pass the username from the login screen to this controller
    public void initialize(String username) {
        this.username = username;
        currentPatient = new Patient(username);
        PatientName.setText("Welcome, " + username);
        System.out.println("Patient logged in with username: " + currentPatient.getUsername() + " and name: " + currentPatient.getFirstName());
    }

    // Load the corresponding page based on the button clicked
    private void loadPage(String fxmlFile, String title, Button currentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newPage = loader.load();

            // Pass the username to the controllers where necessary
            Object controller = loader.getController();
            if (controller instanceof InitializeUsername) {
                ((InitializeUsername) controller).initialize(currentPatient.getUsername());
            }

            Stage currentStage = (Stage) currentButton.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle(title);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Event handlers for different buttons
    public void HandleBookAppointment(ActionEvent actionEvent) {
        loadPage("BookAppointment.fxml", "Book Appointment", BookAppointment);
    }

    public void HandleCompare(ActionEvent actionEvent) {
        loadPage("Comparison.fxml", "Comparison", Compare);
    }

    public void HandleFeedback(ActionEvent actionEvent) {
        loadPage("Submit-Feedback.fxml", "Feedback", Feedback);
    }

    public void HandleEmergency(ActionEvent actionEvent) {
        loadPage("Emergency.fxml", "Emergency", emergency);
    }

    public void HandleFaqs(ActionEvent actionEvent) {
        loadPage("PatientFaq.fxml", "FAQs", faqsButton);
    }

    public void HandlePayments(ActionEvent actionEvent) {
        loadPage("Pay-Bills.fxml", "Bills", payment);
    }

    public void handlepatlogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            System.out.println("Patient logged out");

            Stage currentStage = (Stage) patlogout.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Home Page");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
