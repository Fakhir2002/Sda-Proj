package com.example.proj;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class HomePageController {

    @FXML
    private Button registerPatientButton;

    @FXML
    private Button registerDoctorButton;

    @FXML
    private Button registerStaffButton;

    @FXML
    private Button registerAdminButton;

    @FXML
    private Button faqsButton;

    @FXML
    private Button aboutSyntegrityButton;

    // Method for "Register as Patient" button
    @FXML
    void handleRegisterPatient(ActionEvent event) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientRegister.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) registerPatientButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("HomePage");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Method for "Register as Doctor" button
    @FXML
    void handleRegisterDoctor(ActionEvent event) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterDoctor.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) registerDoctorButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("Register Doctor");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    // Method for "Register as Staff Member" button
    @FXML
    void handleRegisterStaff(ActionEvent event) {
        System.out.println("Register as Staff Member button clicked.");
        // Add your logic here
    }

    // Method for "Register as Admin" button
    @FXML
    void handleRegisterAdmin(ActionEvent event) {

    }

    // Method for "FAQs" button
    @FXML
    void handleFaqs(ActionEvent event) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Faq-view.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) faqsButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("FAQs");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }

    @FXML
    private void handleAboutSyntegrity(ActionEvent event) {
        try {
            // Load the FXML for the About Us application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutUs.fxml")); // Ensure AboutUs.fxml exists in the same directory
            Parent newPage = loader.load();

            Stage currentStage = (Stage) aboutSyntegrityButton.getScene().getWindow();

            // Create a new stage

            currentStage.setScene(new Scene(newPage));
            currentStage.setTitle("About Syntegrity");
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Debugging in case of issues loading the FXML
        }
    }
}
