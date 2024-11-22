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

    @FXML
    private Patient currentPatient;

    // Setter to pass the username from the login screen to this controller
    public void initilize(String username) {
        // Variable to store the username
        PatientName.setText("Welcome, " + username); // Set the label text to display the username
        currentPatient= new Patient(username);
        System.out.println("Patient logged in with username: " + currentPatient.getUsername());

    }

    public void HandleBookAppointment(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookAppointment.fxml"));
            Parent newPage = loader.load();

            Stage currentStage = (Stage) BookAppointment.getScene().getWindow();
            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("BookAppointment");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
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
            e.printStackTrace(); // Debugging in case of issues loading the FXML
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
            e.printStackTrace(); // Debugging in case of issues loading the FXML
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
            e.printStackTrace(); // Debugging in case of issues loading the FXML
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
            e.printStackTrace(); // Debugging in case of issues loading the FXML
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
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    public void handlepatlogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent newPage = loader.load();

            currentPatient=null;
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
